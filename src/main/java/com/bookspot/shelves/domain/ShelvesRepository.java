package com.bookspot.shelves.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelvesRepository extends JpaRepository<Shelves, Long> {
    List<Shelves> findByUsersId(long userId);

    @Query("select s from Shelves s where s.users.id = :userId and s.isPublic = true")
    List<Shelves> findPublicShelvesBy(@Param("userId") long userId);

    @Query("SELECT s FROM Shelves s JOIN FETCH s.users WHERE s.id = :shelfId")
    Optional<Shelves> findWithUser(@Param("shelfId") long shelfId);
}
