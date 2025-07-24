package com.bookspot.book.infra.search;

import com.bookspot.book.infra.search.builder.BookQueryBuilder;
import com.bookspot.book.infra.search.builder.BookSearchRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookOpenSearchRepository implements BookSearchRepository {
    private final OpenSearchClient client;

    private final BookSearchRequestBuilder searchRequestBuilder;
    private final BookQueryBuilder queryBuilder;

    @Override
    public BookPageResult search(BookSearchCond searchRequest, Pageable pageable) {
        if(searchRequest == null || pageable == null)
            throw new IllegalArgumentException("필수 조건 누락");
        if(pageable.getOffset() + pageable.getPageSize() >= 10_000)
            throw new IllegalArgumentException("Pageable 검색 시 1만건 이하의 offset만 검색 가능");


        try {
            SearchResponse<BookDocument> resp = client.search(
                    searchRequestBuilder.build(
                            queryBuilder.buildBool(searchRequest), pageable
                    ),
                    BookDocument.class
            );

            return createPageResult(resp, pageable);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BookSearchAfterResult search(
            BookSearchCond searchCond,
            SearchAfterCond searchAfterCond,
            int pageSize
    ) {
        if(searchCond == null || searchAfterCond == null)
            throw new IllegalArgumentException("필수 조건 누락");

        try {
            SearchResponse<BookDocument> resp = client.search(
                    searchRequestBuilder.build(
                            queryBuilder.buildBool(searchCond),
                            searchAfterCond,
                            pageSize
                    ),
                    BookDocument.class
            );

            return createSearchAfterResult(resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BookSearchAfterResult createSearchAfterResult(SearchResponse<BookDocument> resp) {
        List<BookDocument> list = resp.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        return new BookSearchAfterResult(
                list,
                list.getLast().getLoanCount(),
                list.getLast().getId(),
                resp.hits().total().value()
        );
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
}
