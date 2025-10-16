package com.bookspot.book.infra.search.pagination;

import org.opensearch.client.opensearch._types.FieldSort;
import org.opensearch.client.opensearch._types.SortOptions;
import org.opensearch.client.opensearch._types.SortOrder;

import java.util.List;

public class BookSortOptions {
    public static final List<SortOptions> SORT_BY_LOAN_COUNT = List.of(
            buildSort("loan_count", SortOrder.Desc),
            buildSort("_score", SortOrder.Desc),
            buildSort("book_id", SortOrder.Asc)
    );

    public static final List<SortOptions> SORT_BY_SCORE  = List.of(
            buildSort("_score", SortOrder.Desc),
            buildSort("loan_count", SortOrder.Desc),
            buildSort("book_id", SortOrder.Asc)
    );

    public static final List<SortOptions> RANKING_SORT = List.of(
            buildSort("rank", SortOrder.Asc),
            buildSort("book_id", SortOrder.Asc)
    );

    private static SortOptions buildSort(String fieldName, SortOrder order) {
        FieldSort.Builder sortBuilder = new FieldSort.Builder();

        FieldSort sortOption = sortBuilder.field(fieldName)
                .order(order)
                .build();

        return new SortOptions.Builder()
                .field(sortOption)
                .build();
    }
}
