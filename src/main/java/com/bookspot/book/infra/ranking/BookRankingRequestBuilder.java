package com.bookspot.book.infra.ranking;

import com.bookspot.book.infra.search.pagination.OpenSearchAfter;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import com.bookspot.global.consts.Indices;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.springframework.stereotype.Service;

@Service
public class BookRankingRequestBuilder {
    public SearchRequest build(Query query, OpenSearchPageable pageable) {
        SearchRequest.Builder builder = new SearchRequest.Builder();
        builder.index(Indices.BOOK_RANKING_INDEX);
        builder.query(query);
        builder.from(pageable.getOffset());
        builder.size(pageable.getPageSize());
        builder.sort(pageable.getSortOptions());

        return builder.build();
    }
}
