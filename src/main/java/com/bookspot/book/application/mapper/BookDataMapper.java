package com.bookspot.book.application.mapper;

import com.bookspot.book.infra.search.BookDocument;
import com.bookspot.book.presentation.BookSummaryResponse;
import com.bookspot.book.presentation.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDataMapper {
    public static BookSummaryResponse transform(BookDocument book) {
        List<CategoryResponse> categories = book.getBookCategories().stream()
                .map(BookDataMapper::transform)
                .toList();

        CategoryResponse leafCategory = categories.getLast();
        return new BookSummaryResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn13(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getLoanCount(),
                leafCategory
        );
    }

    private static CategoryResponse transform(String category) {
        int dotIdx = category.indexOf(".");
        return new CategoryResponse(
                Integer.parseInt(category.substring(0, dotIdx)),
                category.substring(dotIdx + 1)
        );
    }
}
