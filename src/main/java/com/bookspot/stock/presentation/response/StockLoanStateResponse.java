package com.bookspot.stock.presentation.response;

import java.time.LocalDate;

public record StockLoanStateResponse(
        long stockId,
        LocalDate stateUpdatedAt,
        LoanStateResponseEnum loanState
) {
}
