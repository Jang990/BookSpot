package com.bookspot.book.infra.search.builder;

import com.bookspot.book.infra.search.cond.BookSearchCond;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.util.ObjectBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class BookQueryBuilder {
    public Query buildBool(BookSearchCond searchRequest) {
        BoolQuery.Builder builder = new BoolQuery.Builder();

        if (searchRequest.hasBookIds())
            builder.filter(ids(searchRequest.getBookIds()));
        if (searchRequest.hasLibraryId())
            builder.filter(term("library_ids", searchRequest.getLibraryId().toString()));

        if (searchRequest.hasKeyword())
            builder.minimumShouldMatch("1")
                    .should(
                            matchPhrase("title", searchRequest.getKeyword(), 1),
                            match("title.ngram", searchRequest.getKeyword()),
                            matchPhrase("author", searchRequest.getKeyword()),
                            term("publisher", searchRequest.getKeyword())
                    );

        return new Query.Builder()
                .bool(builder.build())
                .build();
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

    private Query term(String fieldName, String keyword) {
        return new Query.Builder()
                .term(
                        mp -> mp.field(fieldName)
                                .value(fv-> fv.stringValue(keyword))
                )
                .build();
    }

    private Query match(String fieldName, String keyword) {
        return new Query.Builder()
                .match(mp -> mp.field(fieldName)
                        .query(f -> f.stringValue(keyword))
                )
                .build();
    }

    private Query matchPhrase(String fieldName, String keyword) {
        return new Query.Builder()
                .matchPhrase(mp -> mp.field(fieldName).query(keyword))
                .build();
    }

    private Query matchPhrase(String fieldName, String keyword, int slopCnt) {
        return new Query.Builder()
                .matchPhrase(mp -> mp.field(fieldName).query(keyword).slop(slopCnt))
                .build();
    }
}
