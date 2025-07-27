package com.bookspot.book.infra.search.cond;

import com.bookspot.category.application.BookCategoryDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Builder
public class BookSearchCond {
    private List<Long> bookIds;
    private String keyword;
    private Long libraryId;
    private BookCategoryDto categoryFilter;

    public boolean hasBookIds() {
        return bookIds != null && !bookIds.isEmpty();
    }

    public boolean hasKeyword() {
        return StringUtils.hasText(keyword);
    }

    public boolean hasLibraryId() {
        return libraryId != null;
    }

    public boolean hasCategoryFilter() {
        return categoryFilter != null;
    }
}
