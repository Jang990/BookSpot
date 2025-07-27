package com.bookspot.category.infra;

import com.bookspot.category.domain.BookCategory;
import com.bookspot.category.domain.BookCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookCategoryCacheRepositoryTest {
    @Autowired
    BookCategoryRepository bookCategoryRepository;

//    @Test
    void test() {
        BookCategory bookCategory = bookCategoryRepository.findById(11).get();
        System.out.println(bookCategory.getName());
        System.out.println(bookCategory.getParentBookCategory().getName());
        System.out.println(bookCategory.getParentBookCategory().getParentBookCategory().getName());
    }
}