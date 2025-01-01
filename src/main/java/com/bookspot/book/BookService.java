package com.bookspot.book;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public List<String> findAll(List<Long> bookIds) {
        List<Long> ids = bookIds.stream().map(id -> {
            if (id > 5)
                return id - 5;
            return id;
        }).toList();

        return repository.findAllById(ids).stream()
                .map(Book::getName)
                .toList();
    }
}
