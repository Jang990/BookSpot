package com.bookspot.book.presentation.request;

import com.bookspot.book.infra.RankingAge;
import com.bookspot.book.infra.RankingGender;
import com.bookspot.book.infra.RankingPeriod;
import jakarta.validation.constraints.NotNull;

public record BookRankingRequest(
        @NotNull RankingGender gender,
        @NotNull RankingAge age,
        @NotNull RankingPeriod period
) {
    public BookRankingRequest {
        if (gender == null) gender = RankingGender.ALL;
        if (age == null) age = RankingAge.ALL;
    }
}
