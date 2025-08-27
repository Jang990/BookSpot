package com.bookspot.stock.event;

import com.bookspot.book.domain.Book;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LibraryStockRepository;
import com.bookspot.stock.domain.event.StockRefreshedEvent;
import com.bookspot.stock.domain.service.loanable.LoanStateApiClient;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import com.bookspot.stock.domain.service.loanable.exception.ClientException;
import com.bookspot.stock.domain.service.loanable.exception.ServerException;
import com.bookspot.stock.domain.service.loanable.exception.TooManyRequestsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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
        } catch (TooManyRequestsException e) {
            log.info("Reqeust 한도 초과로 stock refresh요청 실패");
        } catch (ClientException e) {
            log.error("Request 구조 설계 실패로 코드 수정 필요함");
        } catch (ServerException e) {
            log.info("외부 API 서버 오류로 일시적으로 refresh 요청 실패");
        }
    }
}
