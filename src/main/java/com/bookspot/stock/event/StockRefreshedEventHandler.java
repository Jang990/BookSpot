package com.bookspot.stock.event;

import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LibraryStockRepository;
import com.bookspot.stock.domain.event.StockRefreshedEvent;
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

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(
            classes = StockRefreshedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(StockRefreshedEvent event) {
        LibraryStock libraryStock = libraryStockRepository.findStock(event.libraryStockId())
                .orElseThrow(IllegalArgumentException::new);
    }
}
