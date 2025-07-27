package com.bookspot.category.application;

import com.bookspot.category.domain.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
public class BookCategoryDto {
    private final int id;
    private final String name;

    public BookCategoryDto(BookCategory bookCategory) {
        this.id = bookCategory.getId();
        this.name = bookCategory.getName();
    }

    public BookCategoryDto(int id, String name) {
        Objects.requireNonNull(name);

        this.id = id;
        this.name = name;
    }
}
