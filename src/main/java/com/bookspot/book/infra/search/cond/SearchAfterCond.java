package com.bookspot.book.infra.search.cond;

import com.bookspot.book.infra.search.pagination.BookSortOptions;
import org.opensearch.client.opensearch._types.SortOptions;

import java.util.List;

public record SearchAfterCond(
        String lastScore,
        long lastLoanCount,
        long lastBookId,
        int pageSize,
        List<SortOptions> sortOptions
) {
    public List<String> getSearchAfterValues() {
        return List.of(
                String.valueOf(lastLoanCount),
                lastScore,
                String.valueOf(lastBookId)
        );
    }

    public boolean hasScoreSortOption() {
        return sortOptions == BookSortOptions.SORT_BY_SCORE;
    }
}
