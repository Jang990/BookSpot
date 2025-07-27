package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import com.bookspot.book.presentation.consts.BookBindingError;
import com.bookspot.book.presentation.consts.BookRequestCond;
import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import com.bookspot.book.presentation.request.BookSearchRequest;
import com.bookspot.book.presentation.response.BookDetailResponse;
import com.bookspot.book.presentation.response.BookPreviewPageResponse;
import com.bookspot.book.presentation.response.BookPreviewResponse;
import com.bookspot.book.presentation.response.BookPreviewSearchAfterResponse;
import com.bookspot.global.log.BasicLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@BasicLog
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping(value = "/api/books", params = {
            BookSearchAfterRequest.IGNORE_PARAM_LAST_LOAN_COUNT,
            BookSearchAfterRequest.IGNORE_PARAM_LAST_BOOK_ID
    })
    public ResponseEntity<BookPreviewPageResponse> findBook(
            @Valid BookSearchRequest request,
            Pageable pageable,
            BindingResult bindingResult
    ) throws BindException {
        validateRequest(request, pageable, bindingResult);

        return ResponseEntity.ok(
                bookService.findBooks(request, pageable)
        );
    }

    @GetMapping(value = "/api/books", params = {
            BookSearchAfterRequest.PARAM_LAST_LOAN_COUNT,
            BookSearchAfterRequest.PARAM_LAST_BOOK_ID
    })
    public ResponseEntity<BookPreviewSearchAfterResponse> findBook(
            @Valid BookSearchRequest request,
            @Valid BookSearchAfterRequest searchAfterRequest,
            @RequestParam(defaultValue = "12") int size
    ) throws BindException {
        return ResponseEntity.ok(
                bookService.findBooks(
                        request,
                        searchAfterRequest,
                        size
                )
        );
    }

    @GetMapping("/api/books")
    public ResponseEntity<Void> handleInvalidSearchAfterParams() throws BindException {
        BindingResult bindingResult = new BeanPropertyBindingResult(null, "bookSearch");
        bindingResult.addError(BookBindingError.SEARCH_CRITERIA_INVALID.error());
        throw new BindException(bindingResult);
    }

    private void validateRequest(BookSearchRequest request, Pageable pageable, BindingResult bindingResult) throws BindException {
        if(bindingResult.hasErrors())
            throw new BindException(bindingResult);

        if (pageable.getPageNumber() > BookRequestCond.MAX_SEARCH_PAGE_NUMBER) {
            bindingResult.addError(BookBindingError.TOO_LARGE_PAGE_NUMBER.error());
            throw new BindException(bindingResult);
        }

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
