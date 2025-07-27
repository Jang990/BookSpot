package com.bookspot.book.presentation.response;

import org.springframework.data.domain.Page;

public record BookPreviewPageResponse(
        Page<BookPreviewResponse> books,
        Integer lastLoanCount,
        Long lastBookId
) {
}
