package com.bookspot.book.infra.ranking;

import com.bookspot.book.infra.RankingAge;
import com.bookspot.book.infra.RankingGender;
import com.bookspot.book.infra.RankingPeriod;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;

import java.util.List;
import java.util.Objects;

public record BookRankingCond(
        RankingPeriod period,
        RankingGender gender,
        RankingAge age
) {
    public BookRankingCond {
        Objects.requireNonNull(period);
        Objects.requireNonNull(gender);
        Objects.requireNonNull(age);
    }

    public Query toBoolQuery() {
        BoolQuery.Builder builder = new BoolQuery.Builder();

        builder.filter(
                term("ranking_type", period.toString()),
                term("ranking_gender", gender.toString()),
                term("ranking_age", age.toString())
        );

        return new Query.Builder()
                .bool(builder.build())
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
