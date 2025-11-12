package com.bookspot.book.infra.search;

import com.bookspot.book.infra.BookDocument;
import com.bookspot.book.infra.BookSearchRepository;
import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.book.infra.search.cond.YearRange;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import com.bookspot.book.infra.search.result.BookPageResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
@Profile("prod")
class BookOpenSearchRepositoryTest {
    @Autowired
    BookSearchRepository repository;

//    @Test
    void 검색테스트() {
        /*
        22286 - 게으른 완벽주의자를 위한 심리학
        26663 - 이기적 유전자 (40주년 기념판) {2, 5, 6 ... }
        33158 - (개싸움을 지적 토론의 장으로 만드는) 어른의 문답법
        85303 - 이기적 유전자:진화론의 새로운 패러다임 {1, 4, 5, ...}
         */
        BookSearchCond request = BookSearchCond.builder()
                .bookIds(List.of(22286L, 26663L, 33158L, 85303L))
                .keyword("이기적 유전자")
                .build();

        PageRequest pageable = PageRequest.of(0, 10);
        BookPageResult result = repository.search(request, OpenSearchPageable.sortByLoanCount(pageable));
        for (BookDocument bookDocument : result.books()) {
            System.out.println(bookDocument.getTitle() + " " + bookDocument.getLoanCount());
        }
    }

    @ParameterizedTest
    @MethodSource("args")
    void 발행연도_검색_테스트(YearRange yearRange, int expectedResultCnt) {
        /*
        101447 : 2015년 - 책 먹는 여우
        92364 : 2016년 - 채식주의자
        78234 : 2017년 - 나미야 잡화점의 기적:히가시노 게이고 장편소설
        68890 : 2018년 - 7년의 밤:정유정 장편소설
         */
        BookSearchCond request = BookSearchCond.builder()
                .bookIds(List.of(101447L, 92364L, 78234L, 68890L))
                .yearRange(yearRange)
                .build();

        PageRequest pageable = PageRequest.of(0, 10);
        BookPageResult result = repository.search(request, OpenSearchPageable.sortByLoanCount(pageable));
        Assertions.assertEquals(result.books().getTotalElements(), expectedResultCnt);
    }

    private static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(new YearRange(2015, 2017), 3),
                Arguments.of(new YearRange(2017, 9999), 2),
                Arguments.of(new YearRange(0, 2015), 1)
        );
    }
}