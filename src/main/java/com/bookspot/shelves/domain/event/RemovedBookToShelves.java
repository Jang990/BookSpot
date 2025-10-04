package com.bookspot.shelves.domain.event;

import java.util.List;

public record RemovedBookToShelves(List<Long> shelfIds, long bookId) {
}
