package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/book")
    @ResponseBody
    public ResponseEntity<Slice<BookSummaryResponse>> findBook(
            @Valid BookSearchRequest request,
            Pageable pageable) {
        if(pageable.getPageSize() > 50)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.findBook(request.getTitle(), pageable));
    }

    @GetMapping("/libraries/stock/book")
    public String searchBookPage() {
        return "book/search";
    }
}
