package com.bookspot.book.presentation.request;

import com.bookspot.category.presentation.CategoryRequestCond;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
@AllArgsConstructor
public class BookSearchRequest {
    private static final BookSort DEFAULT_SORT_BY = BookSort.RELEVANCE;

    @Size(min = 2)
    private String title;
    private List<Long> bookIds;

    @Positive
    private Long libraryId;

    @Range(
            min = CategoryRequestCond.MIN_CATEGORY_ID,
            max = CategoryRequestCond.MAX_CATEGORY_ID
    )
    private Integer categoryId;

    private CategoryLevel categoryLevel;
    private BookSort sortBy;

    public BookSort getSortBy() {
        return sortBy == null ? DEFAULT_SORT_BY : sortBy;
    }
}
