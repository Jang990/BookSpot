package com.bookspot.stock.presentation.response;

import java.util.List;

public record LibraryStockResponse(List<Long> availableBookIds, List<Long> unavailableBookIds) {
    public int totalBooksCount() {
        return availableBookIds.size() + unavailableBookIds.size();
    }
}
