package com.bookspot.stock.domain;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryStockRepository extends JpaRepository<LibraryStock, Long> {
    boolean existsByLibraryIdAndBookId(Long libraryId, Long bookId);

    // DB innodb_lock_wait_timeout 파라미터 변경 필요
    @Query("""
            select ls
            from LibraryStock ls
                join fetch ls.book b
                join fetch ls.library l
            where ls.id = :id
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "10000")})
    Optional<LibraryStock> findStock(@Param("id") Long id);

    @Query("""
            select ls
            from LibraryStock ls
            where ls.library.id = :libraryId AND ls.book.id in (:bookIds)
            """)
    List<LibraryStock> findLibraryStocks(
            @Param("libraryId") Long libraryId,
            @Param("bookIds") List<Long> bookIds
    );
}
