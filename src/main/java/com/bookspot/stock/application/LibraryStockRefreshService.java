package com.bookspot.stock.application;

import com.bookspot.global.DateHolder;
import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LibraryStockRepository;
import com.bookspot.stock.domain.LoanState;
import com.bookspot.stock.domain.LoanStateRefreshService;
import com.bookspot.stock.presentation.response.LoanStateResponseEnum;
import com.bookspot.stock.presentation.response.StockLoanStateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class LibraryStockRefreshService {
    private final LoanStateRefreshService loanStateRefreshService;
    private final LibraryStockRepository libraryStockRepository;
    private final DateHolder dateHolder;

    public StockLoanStateResponse findCurrentLoanState(long stockId) {
        LibraryStock stock = libraryStockRepository.findById(stockId)
                .orElseThrow(IllegalArgumentException::new);
        if(stock.isAlreadyRefreshed(dateHolder))
            return new StockLoanStateResponse(
                    stock.getId(),
                    stock.getUpdatedAt(),
                    toDto(stock.getLoanState())
            );

        LibraryStock stockWithBookAndLibrary = libraryStockRepository.findStock(stockId)
                .orElseThrow(IllegalArgumentException::new);

        LocalDate now = dateHolder.now();
        LoanState result = loanStateRefreshService.refresh(
                stockWithBookAndLibrary,
                stockWithBookAndLibrary.getBook(),
                stockWithBookAndLibrary.getLibrary()
        );

        return new StockLoanStateResponse(
                stock.getId(),
                now,
                toDto(result)
        );
    }

    public LoanStateResponseEnum toDto(LoanState loanState) {
        return switch (loanState) {
            case LOANABLE -> LoanStateResponseEnum.LOANABLE;
            case ON_LOAN -> LoanStateResponseEnum.ON_LOAN;
            case UNKNOWN -> LoanStateResponseEnum.UNKNOWN;
            case ERROR -> LoanStateResponseEnum.ERROR;
        };
    }
}
