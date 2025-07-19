package com.bookspot.book.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class BookSummaryResponse {
    private final long id;
    private final String title;
    private String author;
    private String isbn13;
    private Integer publicationYear;
    private String publisher;
    private int loanCount;
    private CategoryResponse category;
}
