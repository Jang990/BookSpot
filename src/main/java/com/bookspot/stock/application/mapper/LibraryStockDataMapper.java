package com.bookspot.stock.application.mapper;

import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LoanState;
import com.bookspot.stock.presentation.response.LoanStateResponseEnum;
import com.bookspot.stock.presentation.response.StockLoanStateResponse;

import java.time.LocalDateTime;

public class LibraryStockDataMapper {
    public static StockLoanStateResponse transform(
            long stockId,
            long bookId,
            long libraryId,
            LocalDateTime stateUpdatedAt,
            LoanState loanState
    ) {
        return new StockLoanStateResponse(
                stockId, libraryId, bookId,
                stateUpdatedAt.toString(),
                transform(loanState)
        );
    }

    public static StockLoanStateResponse transform(LibraryStock stock) {
        return new StockLoanStateResponse(
                stock.getId(),
                stock.getLibrary().getId(),
                stock.getBook().getId(),
                stock.getUpdatedAt().toString(),
                transform(stock.getLoanState())
        );
    }

    public static LoanStateResponseEnum transform(LoanState loanState) {
        return switch (loanState) {
            case LOANABLE -> LoanStateResponseEnum.LOANABLE;
            case ON_LOAN -> LoanStateResponseEnum.ON_LOAN;
            case UNKNOWN -> LoanStateResponseEnum.UNKNOWN;
            case ERROR -> LoanStateResponseEnum.ERROR;
        };
    }
}
