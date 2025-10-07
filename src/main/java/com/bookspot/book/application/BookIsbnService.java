package com.bookspot.book.application;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.shelfbooks.domain.ShelfBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookIsbnService {
    private final BookRepository bookRepository;

    public Map<Long, String> findBookIsbn(List<ShelfBook> shelfBooks) {
        Set<Long> thumbnailBookIds = shelfBooks.stream()
                .map(ShelfBook::getBook)
                .map(Book::getId)
                .collect(Collectors.toSet());

        return bookRepository.findAllById(thumbnailBookIds)
                .stream()
                .collect(
                        Collectors.toMap(
                                Book::getId,
                                Book::getIsbn13
                        )
                );
    }
}
