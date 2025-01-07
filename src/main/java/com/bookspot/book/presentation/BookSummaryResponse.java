package com.bookspot.book.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookSummaryResponse {
    private final long id;
    private final String title;
}
