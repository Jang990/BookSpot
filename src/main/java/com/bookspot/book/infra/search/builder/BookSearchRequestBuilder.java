package com.bookspot.book.infra.search.builder;

import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.infra.search.pagination.BookSortOptions;
import com.bookspot.book.infra.search.pagination.OpenSearchAfter;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
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
    public SearchRequest build(Query query, OpenSearchPageable pageable) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(Indices.BOOK_INDEX);
        builder.query(query);

        if(pageable.hasScoreSortOption())
            builder.minScore(50d);
        builder.from(pageable.getOffset());
        builder.size(pageable.getPageSize());
        builder.sort(pageable.getSortOptions());

        return builder.build();
    }

    public SearchRequest build(
            Query query,
            OpenSearchAfter searchAfter
    ) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(Indices.BOOK_INDEX);
        builder.query(query);

        builder.size(searchAfter.getPageSize());
        builder.searchAfter(searchAfter.getSearchAfter());
        builder.sort(searchAfter.getSortOptions());

        return builder.build();
    }
}
