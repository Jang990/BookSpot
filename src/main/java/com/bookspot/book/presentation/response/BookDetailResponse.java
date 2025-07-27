package com.bookspot.book.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
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
