package com.bookspot.stock.domain.event;

@Deprecated
public record LoanStateRetryableErrorEvent(long libraryId, long bookId) {
}
