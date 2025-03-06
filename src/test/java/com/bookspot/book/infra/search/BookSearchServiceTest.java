package com.bookspot.book.infra.search;

import com.bookspot.book.presentation.BookDetailResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookSearchServiceTest {
    @Autowired
    BookSearchService service;

    @Test
    void test() {
        Slice<BookDetailResponse> result = service.findBook("한강", PageRequest.of(0, 1000));
        System.out.println(result.getContent().size());
        for (BookDetailResponse bookDetailResponse : result.getContent()) {
            System.out.println(bookDetailResponse);
        }
    }

}