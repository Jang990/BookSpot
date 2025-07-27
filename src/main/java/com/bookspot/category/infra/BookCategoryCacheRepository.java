package com.bookspot.category.infra;

import com.bookspot.category.domain.BookCategory;
import com.bookspot.category.domain.BookCategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookCategoryCacheRepository implements BookCategoryRepository {
    private final BookCategoryJpaRepository jpaRepository;
    private final HashMap<Integer, BookCategory> cache;


    @PostConstruct
    public void init() {
        jpaRepository.findAll()
            .forEach(cat -> cache.put(cat.getId(), cat));
    }

    @Override
    public List<BookCategory> findAll() {
        return cache.values().stream().toList();
    }

    @Override
    public Optional<BookCategory> findById(int id) {
        return Optional.ofNullable(cache.get(id));
    }
}
