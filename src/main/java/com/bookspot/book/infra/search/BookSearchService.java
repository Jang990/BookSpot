package com.bookspot.book.infra.search;

import com.bookspot.book.presentation.BookDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookSearchService {
    private final BookSearchRepository bookSearchRepository;

    public Page<BookDetailResponse> findBook(String keyword, Pageable pageable) {
        Page<BookDocument> bookDocuments = bookSearchRepository.findWithKeyword(keyword, pageable);

        return new PageImpl<>(
                bookDocuments.stream().map(this::toBookDetail).toList(),
                pageable, bookDocuments.getTotalElements()
        );
    }

    private BookDetailResponse toBookDetail(BookDocument content) {
        return new BookDetailResponse(
                content.getId(),
                content.getTitle(),
                content.getIsbn13(),
                content.getSubjectCode(),
                content.getAuthor(),
                content.getPublicationYear(),
                content.getPublisher()
        );
    }
}
