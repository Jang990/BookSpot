package com.bookspot.book.presentation.response;

import com.bookspot.book.infra.RankingAge;
import com.bookspot.book.infra.RankingGender;
import com.bookspot.book.infra.RankingPeriod;
import lombok.Getter;

@Getter
public class BookRankPreviewResponse extends BookPreviewResponse{
    private int rank;
    private int loanIncrease;

    private RankingPeriod rankingPeriod;
    private RankingAge rankingAge;
    private RankingGender rankingGender;

    public BookRankPreviewResponse(
            BookPreviewResponse bookPreviewResponse,
            int rank,
            int loanIncrease,
            RankingPeriod rankingPeriod,
            RankingAge rankingAge,
            RankingGender rankingGender
    ) {
        super(
                bookPreviewResponse.getId(), bookPreviewResponse.getTitle(),
                bookPreviewResponse.getAuthor(), bookPreviewResponse.getIsbn13(),
                bookPreviewResponse.getPublicationYear(), bookPreviewResponse.getPublisher(),
                bookPreviewResponse.getLoanCount(), bookPreviewResponse.getCategory(),
                bookPreviewResponse.getCreatedAt()
        );

        this.rank = rank;
        this.loanIncrease = loanIncrease;
        this.rankingPeriod = rankingPeriod;
        this.rankingAge = rankingAge;
        this.rankingGender = rankingGender;
    }
}
