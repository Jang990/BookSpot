package com.bookspot.category.application;

import com.bookspot.category.domain.BookCategory;
import lombok.Getter;

@Getter
public class BookCategoryDto {
    private final int id;
    private final String name;

    public BookCategoryDto(BookCategory bookCategory) {
        this.id = bookCategory.getId();
        this.name = bookCategory.getName();
    }
}
