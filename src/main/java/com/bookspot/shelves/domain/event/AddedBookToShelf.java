package com.bookspot.shelves.domain.event;

public record AddedBookToShelf(Long shelfId, Long bookId) {
}
