package com.bookspot.stock.event;

import com.bookspot.book.domain.Book;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LibraryStockRepository;
import com.bookspot.stock.domain.event.StockRefreshedEvent;
import com.bookspot.stock.domain.service.loanable.LoanStateApiClient;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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

        // TODO: 예외처리 필요. 429, 5xx, 4xx | 일일 3만개의 요청 제한. 2.5만개만 쓸 수 있게 제한 필요함.
        LoanableResult result = loanStateApiClient.request(
                new LoanableSearchCond(
                        library.getLibraryCode(),
                        book.getIsbn13()
                )
        );

        libraryStock.updateLoanState(result);
    }
}
