package com.bookspot.book.infra.search.result;

import com.bookspot.book.infra.search.BookDocument;

import java.util.List;

public record BookSearchAfterResult(
        List<BookDocument> books,
        Integer lastLoanCount,
        Long lastBookId,
        long totalElements
) {
}
