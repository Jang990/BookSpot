package com.bookspot.book.infra.search.cond;

import lombok.Builder;
import lombok.Getter;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Operator;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.util.ObjectBuilder;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Function;

@Getter
@Builder
public class BookSearchCond {
    private List<Long> bookIds;
    private String keyword;
    private String isbn13;
    private Long libraryId;
    private BookCategoryCond categoryCond;

    public boolean hasBookIds() {
        return bookIds != null && !bookIds.isEmpty();
    }

    public boolean hasKeyword() {
        return isbn13 == null && StringUtils.hasText(keyword);
    }

    public boolean hasIsbn13() {
        return keyword == null && StringUtils.hasText(isbn13);
    }

    public boolean hasLibraryId() {
        return libraryId != null;
    }

    public boolean hasCategoryFilter() {
        return categoryCond != null;
    }

    public Query toBoolQuery() {
        BoolQuery.Builder builder = new BoolQuery.Builder();

        if (hasBookIds())
            builder.filter(ids(bookIds));
        if (hasLibraryId())
            builder.filter(term("library_ids", libraryId.toString()));
        if(hasCategoryFilter())
            builder.filter(
                    term(
                            categoryCond.getCategoryField(),
                            categoryCond.getCategoryValue()
                    )
            );

        if (hasIsbn13()) {
            builder.filter(term("isbn13", isbn13));
        }

        if (hasKeyword())
            builder.minimumShouldMatch("1")
                    .should(
                            multiMatch(
                                    List.of(
                                            "title^5",
//                                            "title.ngram^6",
                                            "title.ws^8",
                                            "title.keyword^10",
                                            "author^7",
                                            "publisher^8"
                                    ),
                                    keyword,
                                    Operator.And
                            )
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

    private Query multiMatch(List<String> fields, String keyword, Operator operator) {
        return new Query.Builder()
                .multiMatch(
                        mmq -> mmq.fields(fields)
                                .query(keyword)
                                .operator(operator)
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
