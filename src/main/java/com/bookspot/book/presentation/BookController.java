package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import com.bookspot.book.presentation.consts.BookBindingError;
import com.bookspot.book.presentation.consts.BookRequestCond;
import com.bookspot.global.log.BasicLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@BasicLog
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/api/books")
    @ResponseBody
    public ResponseEntity<Page<BookSummaryResponse>> findBook(
            @Valid BookSearchRequest request,
            Pageable pageable,
            BindingResult bindingResult) throws BindException {
        validateRequest(request, pageable, bindingResult);

        if(request.hasTitle() && request.hasBookIds())
            return ResponseEntity.ok(
                    bookService.findBooks(
                            request.getTitle(),
                            request.getBookIds(),
                            pageable
                    )
            );

        if(request.hasTitle())
            return ResponseEntity.ok(bookService.findBooks(request.getTitle(), pageable));

        return ResponseEntity.ok(bookService.findBooks(request.getBookIds(), pageable));
    }

    private void validateRequest(BookSearchRequest request, Pageable pageable, BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);

        if (pageable.getPageSize() > BookRequestCond.MAX_SEARCH_PAGE_SIZE) {
            bindingResult.addError(BookBindingError.TOO_LARGE_PAGE_SIZE.error());
            throw new BindException(bindingResult);
        }

        if (request.isCriteriaMissing()) {
            bindingResult.addError(BookBindingError.SEARCH_CRITERIA_MISSING.error());
            throw new BindException(bindingResult);
        }
    }

    @GetMapping("/api/books/{bookId}")
    public ResponseEntity<BookDetailResponse> findBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.find(bookId));
    }

}
