package com.bookspot.book.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookDetailResponse {
    private Long id;
    private String title;
    private String isbn13;
    private int subjectCode;
    private String author;
    private Integer publicationYear;
    private String publisher;
}
