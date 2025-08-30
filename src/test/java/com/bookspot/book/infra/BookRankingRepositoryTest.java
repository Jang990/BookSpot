package com.bookspot.book.infra;

import com.bookspot.book.infra.ranking.BookRankingCond;
import com.bookspot.book.infra.ranking.BookRankingResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRankingRepositoryTest {

    @Autowired
    private BookRankingRepository bookRankingRepository;

    @Test
    void test() {
        BookRankingCond bookRankingCond = new BookRankingCond(
                RankingPeriod.MONTHLY,
                RankingGender.ALL,
                RankingAge.AGE_15_19
        );

        BookRankingResult bookRankingResult = bookRankingRepository.searchTop50(bookRankingCond);
        for (BookRankingDocument book : bookRankingResult.books()) {
            System.out.println(book.getRank() + "." + book.getTitle());
        }
    }

}