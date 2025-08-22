package com.bookspot.book.infra.search.result;

import com.bookspot.book.infra.BookDocument;
import org.springframework.data.domain.Page;

public record BookPageResult(Page<BookDocument> books, String lastScore, Integer lastLoanCount, Long lastBookId) {
}
