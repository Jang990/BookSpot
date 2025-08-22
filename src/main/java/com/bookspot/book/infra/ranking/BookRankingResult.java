package com.bookspot.book.infra.ranking;

import com.bookspot.book.infra.BookRankingDocument;
import org.springframework.data.domain.Page;

public record BookRankingResult(Page<BookRankingDocument> books) {
}
