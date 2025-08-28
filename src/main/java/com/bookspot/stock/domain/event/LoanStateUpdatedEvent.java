package com.bookspot.stock.domain.event;

@Deprecated
public record LoanStateUpdatedEvent(long libraryId, long bookId) {
}
