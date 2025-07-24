package com.bookspot.book.presentation.response;

import org.springframework.data.domain.Page;

public record BookPreviewPageResponse(
        Page<BookSummaryResponse> books,
        Integer lastLoanCount,
        Long lastBookId
) {
}
