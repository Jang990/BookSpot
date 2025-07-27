package com.bookspot.book.infra.search.builder;

import com.bookspot.category.application.BookCategoryDto;
import org.springframework.stereotype.Service;

@Service
public class BookCategoryNameBuilder {
    private static final String FORMAT = "%03d.%s";

    protected String build(BookCategoryDto bookCategoryDto) {
        return FORMAT.formatted(bookCategoryDto.getId(), bookCategoryDto.getName());
    }
}
