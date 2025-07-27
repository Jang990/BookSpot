package com.bookspot.category.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "book_code")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookCategory {
    @Id
    private Integer id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private BookCategory parentBookCategory;
}
