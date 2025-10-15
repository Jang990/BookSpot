package com.bookspot.book.infra.search.builder;

import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import com.bookspot.global.consts.Indices;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.springframework.stereotype.Service;

@Service
public class BookSearchRequestBuilder {
    private static final double MIN_SCORE = 50d;
    public SearchRequest build(Query query, OpenSearchPageable pageable) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(Indices.BOOK_INDEX);
        builder.query(query);

        if(pageable.hasScoreSortOption())
            builder.minScore(MIN_SCORE);
        builder.from(pageable.getOffset());
        builder.size(pageable.getPageSize());
        builder.sort(pageable.getSortOptions());

        return builder.build();
    }

    public SearchRequest build(
            Query query,
            SearchAfterCond searchAfter
    ) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(Indices.BOOK_INDEX);
        builder.query(query);
        if (searchAfter.hasScoreSortOption())
            builder.minScore(MIN_SCORE);

        builder.size(searchAfter.pageSize());
        builder.searchAfter(searchAfter.getSearchAfterValues());
        builder.sort(searchAfter.sortOptions());

        return builder.build();
    }
}
