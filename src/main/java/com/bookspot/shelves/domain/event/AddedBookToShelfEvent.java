package com.bookspot.shelves.domain.event;

public record AddedBookToShelfEvent(Long shelfId, Long bookId) {
}
