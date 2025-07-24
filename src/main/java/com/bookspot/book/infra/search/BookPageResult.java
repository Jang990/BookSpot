package com.bookspot.book.infra.search;

import org.springframework.data.domain.Page;

public record BookPageResult(Page<BookDocument> books, Integer lastLoanCount, Long lastBookId) {
}
