package com.bookspot.book.application.mapper;

import com.bookspot.book.application.dto.BookSearchDto;
import com.bookspot.book.infra.BookDocument;
import com.bookspot.book.infra.BookRankingDocument;
import com.bookspot.book.infra.search.cond.BookCategoryCond;
import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import com.bookspot.book.presentation.request.CategoryLevel;
import com.bookspot.book.presentation.response.BookPreviewResponse;
import com.bookspot.book.presentation.response.BookRankPreviewResponse;
import com.bookspot.book.presentation.response.CategoryResponse;
import com.bookspot.category.domain.BookCategory;
import com.bookspot.category.domain.BookCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDataMapper {
    public static BookPreviewResponse transform(BookDocument book) {
        return new BookPreviewResponse(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn13(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getLoanCount(),
                book.hasCategory() ?
                        transform(book.getMainCategory())
                        : CategoryResponse.EMPTY_CATEGORY,
                book.getCreatedAtDate().toString()
        );
    }

    public static BookRankPreviewResponse transform(BookRankingDocument book) {
        return new BookRankPreviewResponse(
                book.getBookId(), book.getTitle(),
                book.getAuthor(), book.getIsbn13(),
                book.getPublicationYear(), book.getPublisher(),
                book.hasCategory() ?
                        BookDataMapper.transform(book.getMainCategory())
                        : CategoryResponse.EMPTY_CATEGORY,
                book.getCreatedAt(), book.getRank(),
                book.getLoanIncrease()
        );
    }

    public static BookSearchCond transform(BookCategoryRepository bookCategoryRepository, BookSearchDto bookSearchDto) {
        BookCategoryCond categoryCond = null;
        if (bookSearchDto.getCategoryId() != null) {
            BookCategory bookCategory = bookCategoryRepository.findById(bookSearchDto.getCategoryId())
                    .orElseThrow(IllegalArgumentException::new);
            categoryCond = createCategoryCond(
                    bookSearchDto.getCategoryLevel(),
                    bookCategory
            );
        }

        return BookSearchCond.builder()
                .keyword(bookSearchDto.getTitle())
                .bookIds(bookSearchDto.getBookIds())
                .libraryId(bookSearchDto.getLibraryId())
                .categoryCond(categoryCond)
                .build();
    }

    private static BookCategoryCond createCategoryCond(
            CategoryLevel categoryLevel,
            BookCategory bookCategory
    ) {
        return switch (categoryLevel) {
            case LEAF -> BookCategoryCond.leaf(bookCategory.getId(), bookCategory.getName());
            case MID -> BookCategoryCond.mid(bookCategory.getId(), bookCategory.getName());
            case TOP -> BookCategoryCond.top(bookCategory.getId(), bookCategory.getName());
        };
    }

    public static CategoryResponse transform(String category) {
        int dotIdx = category.indexOf(".");
        return new CategoryResponse(
                Integer.parseInt(category.substring(0, dotIdx)),
                category.substring(dotIdx + 1)
        );
    }

    public static SearchAfterCond transform(BookSearchAfterRequest searchAfterCond) {
        return new SearchAfterCond(
                searchAfterCond.getLastScore(),
                searchAfterCond.getLastLoanCount(),
                searchAfterCond.getLastBookId()
        );
    }
}
