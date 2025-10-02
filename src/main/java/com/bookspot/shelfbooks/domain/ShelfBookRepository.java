package com.bookspot.shelfbooks.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.shelves.domain.Shelves;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelfBookRepository extends JpaRepository<ShelfBook, Long> {
    boolean existsByShelfAndBook(Shelves shelf, Book book);
}
