package com.bookspot.book.infra.search.pagination;

import lombok.Getter;
import org.opensearch.client.opensearch._types.SortOptions;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
public class OpenSearchPageable {
    private final int offset;
    private final int pageSize;
    private final List<SortOptions> sortOptions;

    private OpenSearchPageable(Pageable pageable, List<SortOptions> sortOptions) {
        if(pageable.getOffset() + pageable.getPageSize() >= 10_000)
            throw new IllegalArgumentException("Pageable 검색 시 1만건 이하의 offset만 검색 가능");

        this.offset = Math.toIntExact(pageable.getOffset());
        this.pageSize = pageable.getPageSize();
        this.sortOptions = sortOptions;
    }

    public static OpenSearchPageable withScore(Pageable pageable) {
        return new OpenSearchPageable(pageable, BookSortOptions.SORT_WITH_SCORE);
    }

    public static OpenSearchPageable onlyLoanCount(Pageable pageable) {
        return new OpenSearchPageable(pageable, BookSortOptions.COMMON_SORT);
    }

    public static OpenSearchPageable withRank(Pageable pageable) {
        return new OpenSearchPageable(pageable, BookSortOptions.RANKING_SORT);
    }

    public boolean hasScoreSortOption() {
        return sortOptions == BookSortOptions.SORT_WITH_SCORE;
    }
}
