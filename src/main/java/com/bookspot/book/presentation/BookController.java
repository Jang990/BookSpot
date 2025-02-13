package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    @ResponseBody
    public ResponseEntity<Slice<BookSummaryResponse>> findBook(
            @Valid BookSearchRequest request,
            Pageable pageable) {
        if(pageable.getPageSize() > 50)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(bookService.findBook(request.getTitle(), pageable));
    }

    @GetMapping("/api/books/{bookId}")
    public ResponseEntity<BookDetailResponse> findBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.find(bookId));
    }


}
