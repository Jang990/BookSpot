package com.bookspot.book.infra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRankingDocument extends BookCommonFields{
    private int rank;
    @JsonProperty("ranking_date")
    private String rankingDate;
    @JsonProperty("loan_increase")
    private int loanIncrease;

    @JsonProperty("ranking_type")
    private RankingPeriod rankingPeriod;

    @JsonProperty("ranking_age")
    private RankingAge rankingAge;

    @JsonProperty("ranking_gender")
    private RankingGender rankingGender;
}
