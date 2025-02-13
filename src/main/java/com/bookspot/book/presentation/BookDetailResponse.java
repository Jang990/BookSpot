package com.bookspot.book.presentation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookDetailResponse {
    private Long id;
    private String title;
    private String isbn13;
    private String classification;
    private String author;
    private Integer publicationYear;
    private String publisher;
    private String volumeName;
}
