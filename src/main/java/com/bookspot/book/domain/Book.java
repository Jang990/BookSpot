package com.bookspot.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn13;

    // TODO: 검색을 위해 suffix(005) prefix(115) 나눠야?
    private int subjectCode; // 005.115

    private String author;
    private Integer publicationYear;
    private String publisher;
    private int loanCount;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
