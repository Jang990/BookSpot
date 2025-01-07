package com.bookspot.book.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Slice<Book> findByTitleContaining(String title, Pageable pageable);
}
