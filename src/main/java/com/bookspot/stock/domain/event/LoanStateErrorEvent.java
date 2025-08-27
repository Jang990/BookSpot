package com.bookspot.stock.domain.event;

public record LoanStateErrorEvent(long libraryId, long bookId) {
}
