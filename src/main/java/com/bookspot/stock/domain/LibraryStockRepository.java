package com.bookspot.stock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryStockRepository extends JpaRepository<LibraryStock, Long> {
    boolean existsByLibraryIdAndBookId(Long libraryId, Long bookId);
}
