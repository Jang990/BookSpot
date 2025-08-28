package com.bookspot.stock.domain.event;

public record LoanStateRetryableErrorEvent(long libraryId, long bookId) {
}
