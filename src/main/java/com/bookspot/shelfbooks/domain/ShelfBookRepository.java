package com.bookspot.shelfbooks.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.shelves.domain.Shelves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShelfBookRepository extends JpaRepository<ShelfBook, Long> {
    boolean existsByShelfAndBook(Shelves shelf, Book book);
    boolean existsByShelfIdInAndBookId(List<Long> shelfIds, Long bookId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM ShelfBook sb WHERE sb.shelf.id IN :shelfIds AND sb.book.id = :bookId")
    int deleteByShelfIdsAndBookId(@Param("shelfIds") List<Long> shelfIds, @Param("bookId") long bookId);
}
