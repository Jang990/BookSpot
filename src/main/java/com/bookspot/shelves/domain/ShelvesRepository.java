package com.bookspot.shelves.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelvesRepository extends JpaRepository<Shelves, Long> {
}
