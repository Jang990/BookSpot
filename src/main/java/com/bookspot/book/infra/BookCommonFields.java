package com.bookspot.book.infra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookCommonFields {
    @JsonProperty("book_id")
    private Long bookId;
    private String isbn13;          // isbn
    private String title;           // 도서명
    private String author;          // 저자
    private String publisher;       // 출판사
    @JsonProperty("publication_year")
    private Integer publicationYear;
    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("subject_code")
    private Integer subjectCode; // 주제분류번호
    @JsonProperty("book_categories")
    private BookCategories bookCategories;

    public boolean hasCategory() {
        return getBookCategories() != null;
    }

    public String getMainCategory() {
        if(hasCategory())
            return getBookCategories().getLeafCategory();
        else
            return null;
    }
}
