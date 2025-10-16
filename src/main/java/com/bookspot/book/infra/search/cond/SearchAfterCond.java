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
        if(sortOptions == BookSortOptions.SORT_BY_SCORE)
            return List.of(
                    lastScore,
                    String.valueOf(lastLoanCount),
                    String.valueOf(lastBookId)
            );
        else if(sortOptions == BookSortOptions.SORT_BY_LOAN_COUNT)
            return List.of(
                    String.valueOf(lastLoanCount),
                    lastScore,
                    String.valueOf(lastBookId)
            );

        throw new IllegalArgumentException("지원하지 않는 순서의 SEARCH_AFTER");
    }

    public boolean hasScoreSortOption() {
        return sortOptions == BookSortOptions.SORT_BY_SCORE;
    }
}
