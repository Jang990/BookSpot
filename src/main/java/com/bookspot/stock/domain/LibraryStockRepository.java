package com.bookspot.stock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryStockRepository extends JpaRepository<LibraryStock, Long> {
    boolean existsByLibraryIdAndBookId(Long libraryId, Long bookId);

    @Query("""
            select ls
            from LibraryStock ls
                join fetch ls.book b
                join fetch ls.library l
            where ls.id = :id
            """)
    Optional<LibraryStock> findStock(@Param("id") Long id);
}
