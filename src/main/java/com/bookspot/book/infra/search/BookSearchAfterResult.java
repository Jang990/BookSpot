package com.bookspot.book.infra.search;

import java.util.List;

public record BookSearchAfterResult(
        List<BookDocument> books,
        Integer lastLoanCount,
        Long lastBookId,
        long totalElements
) {
}
