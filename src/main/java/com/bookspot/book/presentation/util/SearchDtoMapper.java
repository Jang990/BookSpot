package com.bookspot.book.presentation.util;

import com.bookspot.book.application.dto.BookSearchDto;
import com.bookspot.book.presentation.request.BookSearchRequest;

public class SearchDtoMapper {
    public static BookSearchDto transform(BookSearchRequest request) {
        return BookSearchDto.builder()
                .title(request.getTitle())
                .bookIds(request.getBookIds())
                .libraryId(request.getLibraryId())
                .build();
    }

    public static BookSearchDto transform(BookSearchRequest request, int categoryId) {
        return BookSearchDto.builder()
                .title(request.getTitle())
                .bookIds(request.getBookIds())
                .libraryId(request.getLibraryId())
                .categoryId(categoryId)
                .build();
    }
}
