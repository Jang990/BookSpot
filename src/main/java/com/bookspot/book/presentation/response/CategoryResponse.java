package com.bookspot.book.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CategoryResponse {
    public static final CategoryResponse EMPTY_CATEGORY = new CategoryResponse(null, null);
    private final Integer id;
    private final String name;
}
