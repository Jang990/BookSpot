package com.bookspot.book.infra.search;

import com.bookspot.global.consts.Indices;
import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.SortOrder;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.util.ObjectBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookOpenSearchRepository implements BookSearchRepository {
    private final OpenSearchClient client;


    @Override
    public Page<BookDocument> search(String keyword, Pageable pageable) {
        SearchResponse<BookDocument> resp = request(
                q -> q.bool(
                        b -> b.minimumShouldMatch("1")
                                .should(matchPhrase("title", keyword))
                                .should(matchPhrase("author", keyword))
                ),
                pageable
        );

        List<BookDocument> list = resp.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long total = resp.hits().total().value();
        return new PageImpl<>(list, pageable, total);
    }

    @Override
    public Page<BookDocument> search(String keyword, List<Long> ids, Pageable pageable) {
        if(ids.isEmpty())
            return new PageImpl<>(Collections.emptyList(), pageable, 0);

        SearchResponse<BookDocument> resp = request(
                q -> q.bool(
                        b -> b.filter(ids(ids))
                                .minimumShouldMatch("1")
                                .should(matchPhrase("title", keyword))
                                .should(matchPhrase("author", keyword))
                ),
                pageable
        );


        List<BookDocument> list = resp.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long total = resp.hits().total().value();
        return new PageImpl<>(list, pageable, total);
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

    private Function<Query.Builder, ObjectBuilder<Query>> matchPhrase(String fieldName, String keyword) {
        return sh -> sh.matchPhrase(mp -> mp.field(fieldName).query(keyword));
    }
}
