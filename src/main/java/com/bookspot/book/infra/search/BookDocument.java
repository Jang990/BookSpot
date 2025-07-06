package com.bookspot.book.infra.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDocument {
    @JsonProperty("book_id")
    private Long id;
    private String title;
    private String isbn13;
    @JsonProperty("subject_code")
    private int subjectCode;
    private String author;
    @JsonProperty("publication_year")
    private Integer publicationYear;
    private String publisher;
    @JsonProperty("loan_count")
    private int loanCount;
    @JsonProperty("library_ids")
    private List<Long> libraryIds;
    @JsonProperty("book_categories")
    private List<String> bookCategories;
    @JsonProperty("created_at")
    private String createdAt;

    public LocalDate getCreatedAt() {
        return LocalDate.parse(createdAt);
    }
}
