package com.bookspot.stock.domain.event;

@Deprecated
public record LoanStateErrorEvent(long libraryId, long bookId) {
}
