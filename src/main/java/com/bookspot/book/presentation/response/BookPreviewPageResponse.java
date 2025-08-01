package com.bookspot.book.presentation.response;

import org.springframework.data.domain.Page;

public record BookPreviewPageResponse(
        Page<BookPreviewResponse> books,
        String lastScore,
        Integer lastLoanCount,
        Long lastBookId
) {
}
