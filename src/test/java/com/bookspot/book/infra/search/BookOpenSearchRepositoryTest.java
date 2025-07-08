package com.bookspot.book.infra.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
@Profile("prod")
class BookOpenSearchRepositoryTest {
    @Autowired
    BookSearchRepository repository;

//    @Test
    void 검색테스트() {
        Page<BookDocument> result = repository.search("객체지향", PageRequest.of(0, 10));
        for (BookDocument bookDocument : result) {
            System.out.println(bookDocument.getTitle() + " " + bookDocument.getLoanCount());
        }
    }

    @Test
    void 검색테스트2() {
        /*
        22286 - 게으른 완벽주의자를 위한 심리학
        26663 - 이기적 유전자 (40주년 기념판) {2, 5, 6 ... }
        33158 - (개싸움을 지적 토론의 장으로 만드는) 어른의 문답법
        85303 - 이기적 유전자:진화론의 새로운 패러다임 {1, 4, 5, ...}
         */
        BookSearchRequest request = new BookSearchRequest(
                List.of(22286L, 26663L, 33158L, 85303L),
                "이기적 유전자", 5L,
                PageRequest.of(0, 10)
        );

        Page<BookDocument> result = repository.search(request);
        for (BookDocument bookDocument : result) {
            System.out.println(bookDocument.getTitle() + " " + bookDocument.getLoanCount());
        }
    }
}