package com.bookspot.book.infra.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
}