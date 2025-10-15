package com.bookspot.book.infra.search.pagination;

import org.opensearch.client.opensearch._types.SortOptions;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class OpenSearchPageable {
    private final Pageable pageable;
    private final List<SortOptions> sortOptions;

    private OpenSearchPageable(Pageable pageable, List<SortOptions> sortOptions) {
        if(pageable.getOffset() + pageable.getPageSize() >= 10_000)
            throw new IllegalArgumentException("Pageable 검색 시 1만건 이하의 offset만 검색 가능");

        this.pageable = pageable;
        this.sortOptions = sortOptions;
    }

    public static OpenSearchPageable sortByLoanCount(Pageable pageable) {
        return new OpenSearchPageable(pageable, BookSortOptions.COMMON_SORT);
    }

    public static OpenSearchPageable sortByRelevance(Pageable pageable) {
        return new OpenSearchPageable(pageable, BookSortOptions.SORT_BY_SCORE);
    }

    public static OpenSearchPageable withRank(Pageable pageable) {
        return new OpenSearchPageable(pageable, BookSortOptions.RANKING_SORT);
    }

    public int getPageSize() {
        return pageable.getPageSize();
    }

    public int getPageNumber() {
        return pageable.getPageNumber();
    }

    public int getOffset() {
        return (int) pageable.getOffset();
    }

    public List<SortOptions> getSortOptions() {
        return sortOptions;
    }

    public boolean hasScoreSortOption() {
        return sortOptions == BookSortOptions.SORT_WITH_SCORE;
    }
}
