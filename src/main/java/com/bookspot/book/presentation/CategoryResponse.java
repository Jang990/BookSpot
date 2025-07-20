package com.bookspot.book.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CategoryResponse {
    private final int id;
    private final String name;
}
