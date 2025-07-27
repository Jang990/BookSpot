package com.bookspot.book.application.mapper;

import com.bookspot.book.application.dto.BookSearchDto;
import com.bookspot.book.infra.search.BookDocument;
import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import com.bookspot.book.presentation.request.BookSearchRequest;
import com.bookspot.book.presentation.response.BookPreviewResponse;
import com.bookspot.book.presentation.response.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDataMapper {
    public static BookPreviewResponse transform(BookDocument book) {
        return new BookPreviewResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn13(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getLoanCount(),
                getLeafCategory(book.getBookCategories()),
                book.getCreatedAt().toString()
        );
    }

    private static CategoryResponse getLeafCategory(List<String> bookCategories) {
        List<CategoryResponse> categories = bookCategories.stream()
                .map(BookDataMapper::transform)
                .toList();

        CategoryResponse leafCategory = categories.isEmpty() ? CategoryResponse.EMPTY_CATEGORY : categories.getLast();
        return leafCategory;
    }

    public static BookSearchCond transform(BookSearchDto bookSearchDto) {
        return BookSearchCond.builder()
                .keyword(bookSearchDto.getTitle())
                .bookIds(bookSearchDto.getBookIds())
                .libraryId(bookSearchDto.getLibraryId())
                .build();
    }

    private static CategoryResponse transform(String category) {
        int dotIdx = category.indexOf(".");
        return new CategoryResponse(
                Integer.parseInt(category.substring(0, dotIdx)),
                category.substring(dotIdx + 1)
        );
    }

    public static SearchAfterCond transform(BookSearchAfterRequest searchAfterCond) {
        return new SearchAfterCond(
                searchAfterCond.getLastLoanCount(),
                searchAfterCond.getLastBookId()
        );
    }
}
