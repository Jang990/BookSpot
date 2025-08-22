package com.bookspot.book.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookRankPreviewResponse {
    private long id;
    private String title;
    private String author;
    private String isbn13;
    private Integer publicationYear;
    private String publisher;
    private CategoryResponse category;
    private String createdAt;

    private int rank;
    private int loanIncrease;
}
