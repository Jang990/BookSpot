package com.bookspot.shelfbooks.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.shelves.domain.Shelves;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shelf_books")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShelfBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id", nullable = false)
    private Shelves shelf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int idx;

    public ShelfBook(Shelves shelf, Book book, int idx) {
        this.shelf = shelf;
        this.book = book;
        this.idx = idx;
    }
}
