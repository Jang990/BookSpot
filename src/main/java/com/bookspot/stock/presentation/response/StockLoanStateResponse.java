package com.bookspot.stock.presentation.response;

public record StockLoanStateResponse(
        long stockId,
        long libraryId,
        long bookId,
        String subjectCode,
        String stateUpdatedAt,
        LoanStateResponseEnum loanState
) {
}
