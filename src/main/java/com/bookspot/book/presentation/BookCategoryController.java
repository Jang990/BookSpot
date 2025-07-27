package com.bookspot.book.presentation;

import com.bookspot.book.application.BookService;
import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import com.bookspot.book.presentation.request.BookSearchRequest;
import com.bookspot.book.presentation.response.BookPreviewPageResponse;
import com.bookspot.book.presentation.response.BookPreviewSearchAfterResponse;
import com.bookspot.book.presentation.util.SearchDtoMapper;
import com.bookspot.book.presentation.util.SearchRequestValidator;
import com.bookspot.global.log.BasicLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@BasicLog
@RestController
@RequiredArgsConstructor
public class BookCategoryController {
    private final BookService bookService;
    @GetMapping(value = "/api/categories/{categoryId}/books", params = {
            BookSearchAfterRequest.IGNORE_PARAM_LAST_LOAN_COUNT,
            BookSearchAfterRequest.IGNORE_PARAM_LAST_BOOK_ID
    })
    public ResponseEntity<BookPreviewPageResponse> findCategoryBooks(
            @PathVariable("categoryId") int categoryId,
            @Valid BookSearchRequest request,
            Pageable pageable,
            BindingResult bindingResult
    ) throws BindException {
        SearchRequestValidator.validatePageable(pageable, bindingResult);

        return ResponseEntity.ok(
                bookService.findBooks(
                        SearchDtoMapper.transform(request, categoryId),
                        pageable
                )
        );
    }

    @GetMapping(value = "/api/categories/{categoryId}/books", params = {
            BookSearchAfterRequest.PARAM_LAST_LOAN_COUNT,
            BookSearchAfterRequest.PARAM_LAST_BOOK_ID
    })
    public ResponseEntity<BookPreviewSearchAfterResponse> findCategoryBooks(
            @PathVariable("categoryId") int categoryId,
            @Valid BookSearchRequest request,
            @Valid BookSearchAfterRequest searchAfterRequest,
            @RequestParam(defaultValue = "12") int size,
            BindingResult bindingResult
    ) throws BindException {
        SearchRequestValidator.validatePageSize(size, bindingResult);

        return ResponseEntity.ok(
                bookService.findBooks(
                        SearchDtoMapper.transform(request, categoryId),
                        searchAfterRequest,
                        size
                )
        );
    }
}
