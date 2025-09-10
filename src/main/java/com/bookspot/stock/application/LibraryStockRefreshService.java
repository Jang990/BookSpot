package com.bookspot.stock.application;

import com.bookspot.global.DateHolder;
import com.bookspot.stock.application.mapper.LibraryStockDataMapper;
import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LibraryStockRepository;
import com.bookspot.stock.domain.LoanState;
import com.bookspot.stock.domain.LoanStateRefreshService;
import com.bookspot.stock.domain.exception.LibraryStockNotFoundException;
import com.bookspot.stock.presentation.response.StockLoanStateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryStockRefreshService {
    private final LoanStateRefreshService loanStateRefreshService;
    private final LibraryStockRepository libraryStockRepository;
    private final DateHolder dateHolder;

    public StockLoanStateResponse refreshLoanState(long stockId) {
        LibraryStock stock = libraryStockRepository.findById(stockId)
                .orElseThrow(() -> new LibraryStockNotFoundException(stockId));

        loanStateRefreshService.checkRefreshAllowed(stock.getLibrary(), stock);

        // TODO: 락을 다루는 일이기 떄문에 batch 처리와 충돌이 있을 수 있음. 관리 필요. 그래도 only 레코드 락이라...
        LibraryStock stockWithBookAndLibrary = libraryStockRepository.findStock(stockId)
                .orElseThrow(() -> new LibraryStockNotFoundException(stockId));

        LocalDateTime now = dateHolder.now();
        LoanState result = loanStateRefreshService.refresh(
                stockWithBookAndLibrary,
                stockWithBookAndLibrary.getBook(),
                stockWithBookAndLibrary.getLibrary()
        );

        return LibraryStockDataMapper.transform(
                stock.getId(),
                stock.getBook().getId(),
                stock.getLibrary().getId(),
                now, result
        );
    }
}
