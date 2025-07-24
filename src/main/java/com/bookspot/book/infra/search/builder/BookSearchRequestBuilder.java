package com.bookspot.book.infra.search.builder;

import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.global.consts.Indices;
import org.opensearch.client.opensearch._types.FieldSort;
import org.opensearch.client.opensearch._types.SortOptions;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSearchRequestBuilder {
    public SearchRequest build(Query query, Pageable pageable) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(Indices.BOOK_INDEX);
        builder.from((int) pageable.getOffset());
        builder.size(pageable.getPageSize());
        builder.query(query);
        builder.sort(
                List.of(
                        buildSort("loan_count", SortOrder.Desc)
//                        buildSort("book_id", SortOrder.Asc)
                )
        );

        return builder.build();
    }

    public SearchRequest build(
            Query query,
            SearchAfterCond searchAfterCond,
            int pageSize
    ) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(Indices.BOOK_INDEX);
        builder.query(query);

        builder.size(pageSize);
        builder.searchAfter(
                List.of(
                        String.valueOf(searchAfterCond.lastLoanCount()),
                        String.valueOf(searchAfterCond.lastBookId())
                )
        );

        builder.sort(
                List.of(
                        buildSort("loan_count", SortOrder.Desc),
                        buildSort("book_id", SortOrder.Asc)
                )
        );

        return builder.build();
    }

    private SortOptions buildSort(String fieldName, SortOrder order) {
        FieldSort.Builder sortBuilder = new FieldSort.Builder();

        FieldSort sortOption = sortBuilder.field(fieldName)
                .order(order)
                .build();

        return new SortOptions.Builder()
                .field(sortOption)
                .build();
    }
}
