package com.bookspot.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
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
    private String classification; // 005.115

    private String author;
    private int publicationYear;
    private String publisher;
    private String volumeName;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getFullName() {
        if(volumeName == null || volumeName.isBlank())
            return title;
        return title.concat(" (%s)".formatted(volumeName));
    }
}
