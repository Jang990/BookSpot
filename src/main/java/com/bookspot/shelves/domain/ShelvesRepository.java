package com.bookspot.shelves.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelvesRepository extends JpaRepository<Shelves, Long> {
    List<Shelves> findByUsersId(long userId);
}
