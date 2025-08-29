package com.bookspot.stock.presentation.response;

public record StockLoanStateResponse(
        long stockId, long libraryId, long bookId,
        String stateUpdatedAt,
        LoanStateResponseEnum loanState
) {
}
