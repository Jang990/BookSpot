package com.bookspot.book.infra.search;

import com.bookspot.global.consts.Indices;
import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.util.ObjectBuilder;
import org.springframework.data.domain.Page;
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

    @Override
    public Page<BookDocument> search(BookSearchRequest searchRequest) {
        if(searchRequest.getPageable() == null)
            throw new IllegalArgumentException("검색 시 pageable은 필수");

        SearchResponse<BookDocument> resp = request(
                q -> q.bool(
                        builder -> {

                            if(searchRequest.hasBookIds())
                                builder.filter(ids(searchRequest.getBookIds()));
                            if(searchRequest.hasLibraryId())
                                builder.filter(term("library_ids", searchRequest.getLibraryId().toString()));

                            if(searchRequest.hasKeyword())
                                builder.minimumShouldMatch("1")
                                        .should(
                                                matchPhrase("title", searchRequest.getKeyword()),
                                                matchPhrase("author", searchRequest.getKeyword()),
                                                term("publisher", searchRequest.getKeyword())
                                        );

                            return builder;
                        }),
                searchRequest.getPageable()
        );

        List<BookDocument> list = resp.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long total = resp.hits().total().value();
        return new PageImpl<>(list, searchRequest.getPageable(), total);
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

    private SearchResponse<BookDocument> request(Function<Query.Builder, ObjectBuilder<Query>> query, Pageable pageable) {
        try {
            return client.search(
                    s -> s.index(Indices.BOOK_INDEX)
                            .from((int) pageable.getOffset())
                            .size(pageable.getPageSize())
                            .query(query)
                            .sort(
                                    sort -> sort.field(
                                            f -> f.field("loan_count")
                                                    .order(SortOrder.Desc)
                                    )
                            ),
                    BookDocument.class
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Query matchPhrase(String fieldName, String keyword) {
        return new Query.Builder()
                .matchPhrase(mp -> mp.field(fieldName).query(keyword))
                .build();
    }

    private Query term(String fieldName, String keyword) {
        return new Query.Builder()
                .term(
                        mp -> mp.field(fieldName)
                                .value(fv-> fv.stringValue(keyword))
                )
                .build();
    }
}
