package com.bookspot.book.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByTitleContaining(String title, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.id IN :bookIds")
    Page<Book> findAllById(@Param("bookIds") List<Long> bookIds, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title% AND b.id IN :bookIds")
    Page<Book> findBooks(
            @Param("title") String title,
            @Param("bookIds") List<Long> bookIds,
            Pageable pageable);
}
