package com.bookspot.category.domain;

import java.util.List;
import java.util.Optional;

public interface BookCategoryRepository {
    List<BookCategory> findAll();
    Optional<BookCategory> findById(int id);
}
