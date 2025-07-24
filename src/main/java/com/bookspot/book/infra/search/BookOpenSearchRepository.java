package com.bookspot.book.infra.search;

import com.bookspot.book.infra.search.builder.BookQueryBuilder;
import com.bookspot.book.infra.search.builder.BookSearchRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.util.ObjectBuilder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookOpenSearchRepository implements BookSearchRepository {
    private final OpenSearchClient client;

    private final BookSearchRequestBuilder searchRequestBuilder;
    private final BookQueryBuilder queryBuilder;

    @Override
    public BookPageResult search(BookSearchCond searchRequest, Pageable pageable) {
        if(pageable == null)
            throw new IllegalArgumentException("검색 시 pageable은 필수");
        if(pageable.getOffset() + pageable.getPageSize() >= 10_000)
            throw new IllegalArgumentException("Pageable 검색 시 1만건 이하의 offset만 검색 가능");

        SearchResponse<BookDocument> resp = request(
                queryBuilder.buildBool(searchRequest), pageable
        );

        return createPageResult(resp, pageable);
    }

    private BookPageResult createPageResult(SearchResponse<BookDocument> resp, Pageable pageable) {
        List<BookDocument> list = resp.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long total = resp.hits().total().value();
        PageImpl<BookDocument> bookDocuments = new PageImpl<>(list, pageable, total);

        if(list.isEmpty())
            return new BookPageResult(
                    bookDocuments,
                    null,
                    null
            );

        return new BookPageResult(
                bookDocuments,
                list.getLast().getLoanCount(),
                list.getLast().getId()
        );
    }

    private Function<Query.Builder, ObjectBuilder<Query>> ids(List<Long> docIds) {
        return f -> f.ids(
                fn -> fn.values(
                        docIds.stream()
                                .map(String::valueOf)
                                .toList()
                )
        );
    }

    private SearchResponse<BookDocument> request(Query query, Pageable pageable) {
        try {
            return client.search(
                    searchRequestBuilder.build(query, pageable),
                    BookDocument.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
