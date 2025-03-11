package com.bookspot.stock.presentation.response;

import java.util.List;

public record LibraryStockResponse(Long libraryId, int totalBooksCount,List<Long> availableBookIds, List<Long> unavailableBookIds) {
    public LibraryStockResponse(Long libraryId, List<Long> availableBookIds, List<Long> unavailableBookIds) {
        this(libraryId, availableBookIds.size() + unavailableBookIds.size(), availableBookIds, unavailableBookIds);
    }
}
