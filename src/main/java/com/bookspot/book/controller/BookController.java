package com.bookspot.book.controller;

import com.bookspot.book.application.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/book")
    @ResponseBody
    public Slice<BookSummaryResponse> findBook(
            String title,
            Pageable pageable) {
        return bookService.findBook(title, pageable);
    }

    @GetMapping("/libraries/stock/book")
    public String searchBookPage() {
        return "book/search";
    }
}
