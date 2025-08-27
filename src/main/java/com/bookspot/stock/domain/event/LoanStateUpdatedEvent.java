package com.bookspot.stock.domain.event;

public record LoanStateUpdatedEvent(long libraryId, long bookId) {
}
