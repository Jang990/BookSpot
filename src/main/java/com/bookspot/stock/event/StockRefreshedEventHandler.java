package com.bookspot.stock.event;

import com.bookspot.book.domain.Book;
import com.bookspot.global.Events;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LibraryStockRepository;
import com.bookspot.stock.domain.event.LoanStateErrorEvent;
import com.bookspot.stock.domain.event.StockRefreshedEvent;
import com.bookspot.stock.domain.service.loanable.LoanStateApiClient;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import com.bookspot.stock.domain.service.loanable.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/*
사용하지 않음. 하지만 추후 비동기 처리로 성능 개선 가능.
지금은 기능 추가가 급해서 동기처리로 변경됨.
 */
@Deprecated
@Slf4j
@Component
@RequiredArgsConstructor
public class StockRefreshedEventHandler {
    private final LibraryStockRepository libraryStockRepository;
    private final LoanStateApiClient loanStateApiClient;

    // TODO: 락 처리 필요.
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(
            classes = StockRefreshedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(StockRefreshedEvent event) {
        LibraryStock libraryStock = libraryStockRepository.findStock(event.libraryStockId())
                .orElseThrow(IllegalArgumentException::new);

        Book book = libraryStock.getBook();
        Library library = libraryStock.getLibrary();

        try {
            LoanableResult result = loanStateApiClient.request(
                    new LoanableSearchCond(
                            library.getLibraryCode(),
                            book.getIsbn13()
                    )
            );

            libraryStock.updateLoanState(result);
        } catch (LoanStateApiException e) {
            log.warn("API 요청기에서 예외 발생", e);
//            libraryStock.raiseErrorEvent(e);
        }
    }
}
