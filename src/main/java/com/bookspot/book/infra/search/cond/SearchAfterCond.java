package com.bookspot.book.infra.search.cond;

import org.opensearch.client.opensearch._types.SortOptions;

import java.util.List;

public record SearchAfterCond(
        String lastScore,
        long lastLoanCount,
        long lastBookId,
        int pageSize,
        List<SortOptions> sortOptions
) {
}
