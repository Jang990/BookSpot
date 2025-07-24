package com.bookspot.book.presentation.response;

import java.util.List;

public record BookPreviewSearchAfterResponse(
        List<BookPreviewResponse> books,
        Integer lastLoanCount,
        Long lastBookId,
        long totalElements
) {
}
