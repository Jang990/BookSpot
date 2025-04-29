package com.bookspot.book.infra.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

//@SpringBootTest
class BookSearchRepositoryTest {
    @Autowired BookSearchRepository repository;

//    @Test
    void test() {
        Page<BookDocument> result = repository.search("한강", PageRequest.of(0, 10));
        for (BookDocument bookDocument : result) {
            System.out.println(bookDocument.getId() + " " + bookDocument.getTitle());
        }
    }

//    @Test
    void test2() {
        Page<BookDocument> result = repository.search(
                "한강",
                List.of(1479350L, 2669054L, 1992790L),
                PageRequest.of(0, 10)
        );
        for (BookDocument bookDocument : result) {
            System.out.println(bookDocument.getId() + " " + bookDocument.getTitle() + " " + bookDocument.getLibraryIds());
        }
    }

}