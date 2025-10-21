package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import com.bookspot.book.presentation.consts.BookBindingError;
import com.bookspot.book.presentation.request.BookIdsSearchRequest;
import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import com.bookspot.book.presentation.request.BookSearchRequest;
import com.bookspot.book.presentation.response.BookDetailResponse;
import com.bookspot.book.presentation.response.BookPreviewListResponse;
import com.bookspot.book.presentation.response.BookPreviewPageResponse;
import com.bookspot.book.presentation.response.BookPreviewSearchAfterResponse;
import com.bookspot.book.presentation.util.SearchDtoMapper;
import com.bookspot.book.presentation.util.SearchRequestValidator;
import com.bookspot.global.log.BasicLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @BasicLog
    @GetMapping(value = "/api/books/by-ids", params = "bookIds")
    public ResponseEntity<BookPreviewListResponse> findBookWithIds(
            @Valid BookIdsSearchRequest request
    ) {
        return ResponseEntity.ok(bookService.findAll(request.getBookIds()));
    }

    /**
     * @see com.bookspot.category.domain.exception.CategoryNotFoundException
     */
    @GetMapping(value = "/api/books", params = {
            BookSearchAfterRequest.IGNORE_PARAM_LAST_LOAN_COUNT,
            BookSearchAfterRequest.IGNORE_PARAM_LAST_BOOK_ID
    })
    public ResponseEntity<BookPreviewPageResponse> findBook(
            @Valid BookSearchRequest request,
            Pageable pageable,
            BindingResult bindingResult
    ) throws BindException {
        SearchRequestValidator.validatePageable(pageable, bindingResult);
        SearchRequestValidator.validateCategoryCond(
                request.getCategoryId(),
                request.getCategoryLevel(),
                bindingResult
        );

        return ResponseEntity.ok(
                bookService.findBooks(
                        SearchDtoMapper.transform(request),
                        pageable
                )
        );
    }

    /**
     * @see com.bookspot.category.domain.exception.CategoryNotFoundException
     */
    @GetMapping(value = "/api/books", params = {
            BookSearchAfterRequest.PARAM_LAST_LOAN_COUNT,
            BookSearchAfterRequest.PARAM_LAST_BOOK_ID,
            BookSearchAfterRequest.PARAM_LAST_SCORE
    })
    public ResponseEntity<BookPreviewSearchAfterResponse> findBook(
            @Valid BookSearchRequest request,
            @Valid BookSearchAfterRequest searchAfterRequest,
            @RequestParam(defaultValue = "12") int size,
            BindingResult bindingResult
    ) throws BindException {
        SearchRequestValidator.validatePageSize(size, bindingResult);
        SearchRequestValidator.validateCategoryCond(
                request.getCategoryId(),
                request.getCategoryLevel(),
                bindingResult
        );
        SearchRequestValidator.validateNumericScore(
                searchAfterRequest.getLastScore(), bindingResult
        );

        return ResponseEntity.ok(
                bookService.findBooks(
                        SearchDtoMapper.transform(request),
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

    /**
     * @see com.bookspot.book.domain.exception.BookNotFoundException
     */
    @GetMapping("/api/books/{bookId}")
    public ResponseEntity<BookDetailResponse> findBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.find(bookId));
    }

}
