package com.bookspot.category.infra;

import com.bookspot.category.domain.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCategoryJpaRepository extends JpaRepository<BookCategory, Integer> {

}
