package com.bookspot.book.infra.search;

import com.bookspot.book.infra.BookDocument;
import com.bookspot.book.infra.BookSearchRepository;
import com.bookspot.book.infra.search.builder.BookSearchRequestBuilder;
import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.infra.search.pagination.OpenSearchAfter;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import com.bookspot.book.infra.search.result.BookPageResult;
import com.bookspot.book.infra.search.result.BookSearchAfterResult;
import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.SortOptions;
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

    @Override
    public BookPageResult search(
            BookSearchCond searchRequest,
            Pageable pageable,
            OpenSearchPageable pageable_TEMP
    ) {
        if(searchRequest == null || pageable == null || pageable_TEMP == null)
            throw new IllegalArgumentException("필수 조건 누락");

        try {
            SearchResponse<BookDocument> resp = client.search(
                    searchRequestBuilder.build(
                            searchRequest.toBoolQuery(),
                            pageable_TEMP
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
                            searchCond.toBoolQuery(),
                            new OpenSearchAfter(pageSize, searchAfterCond)
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

        long total = resp.hits().total().value();
        if(list.isEmpty())
            return new BookSearchAfterResult(
                    list,
                    null,
                    null,
                    null,
                    total
            );


        Double score = resp.hits().hits().getLast().score();
        return new BookSearchAfterResult(
                list,
                score == null ? null : score.toString(),
                list.getLast().getLoanCount(),
                list.getLast().getBookId(),
                total
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
                    null,
                    null
            );

        Double score = resp.hits().hits().getLast().score();
        return new BookPageResult(
                bookDocuments,
                score == null ? null : score.toString(),
                list.getLast().getLoanCount(),
                list.getLast().getBookId()
        );
    }
}
