package com.bookspot.book.application.mapper;

import com.bookspot.book.infra.search.BookDocument;
import com.bookspot.book.presentation.BookSummaryResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookDataMapper {
    public static BookSummaryResponse transform(BookDocument book) {
        return new BookSummaryResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn13(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getLoanCount()
        );
    }
}
