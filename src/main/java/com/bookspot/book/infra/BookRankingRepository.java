package com.bookspot.book.infra;

import com.bookspot.book.infra.ranking.BookRankingCond;
import com.bookspot.book.infra.ranking.BookRankingResult;

public interface BookRankingRepository {
    BookRankingResult searchTop50(BookRankingCond rankingCond);
}
