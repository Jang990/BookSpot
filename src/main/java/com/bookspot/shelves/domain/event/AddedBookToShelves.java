package com.bookspot.shelves.domain.event;

import java.util.List;

public record AddedBookToShelves(List<Long> shelfIds, long bookId) {
}
