package com.bookspot.book.infra.search.result;

import com.bookspot.book.infra.search.BookDocument;
import org.springframework.data.domain.Page;

public record BookPageResult(Page<BookDocument> books, Integer lastLoanCount, Long lastBookId) {
}
