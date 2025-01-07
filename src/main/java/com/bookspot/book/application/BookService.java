package com.bookspot.book.application;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public List<String> findAll(List<Long> bookIds) {
        return repository.findAllById(bookIds).stream()
                .map(Book::getTitle)
                .toList();
    }
}
