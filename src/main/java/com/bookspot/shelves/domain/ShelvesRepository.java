package com.bookspot.shelves.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelvesRepository extends JpaRepository<Shelves, Long> {
    List<Shelves> findByUsersId(long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Shelves s WHERE s.users.id = :userId AND s.id in :shelfIds")
    List<Shelves> findByIdsWithLock(@Param("userId") long userId, @Param("shelfIds") List<Long> shelfIds);

    @Query("select s from Shelves s where s.users.id = :userId and s.isPublic = true")
    List<Shelves> findPublicShelvesBy(@Param("userId") long userId);

    @Query("SELECT s FROM Shelves s JOIN FETCH s.users WHERE s.id = :shelfId")
    Optional<Shelves> findWithUser(@Param("shelfId") long shelfId);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Shelves s SET s.bookCount = s.bookCount + 1 WHERE s.id IN :ids")
    void increaseBookCountIn(@Param("ids") List<Long> ids);
}
