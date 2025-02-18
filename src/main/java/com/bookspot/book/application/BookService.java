package com.bookspot.book.application;

import com.bookspot.book.presentation.BookDto;
import com.bookspot.book.presentation.BookDetailResponse;
import com.bookspot.book.presentation.BookResponse;
import com.bookspot.book.presentation.BookSummaryResponse;
import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    public List<BookResponse> findAll(List<Long> bookIds) {
        List<Book> books = repository.findAllById(bookIds);
        if(books.size() != bookIds.size())
            throw new IllegalArgumentException("찾을 수 없는 ID가 포함돼 있음");
        return books.stream()
                .map(book -> new BookResponse(book.getId(), book.getTitle()))
                .toList();
    }

    public Slice<BookSummaryResponse> findBooks(String title, Pageable pageable) {
        return repository.findByTitleContaining(title, pageable)
                .map(book -> new BookSummaryResponse(
                        book.getId(),
                        book.getFullName(),
                        book.getAuthor(),
                        book.getPublicationYear(),
                        book.getPublisher()
                ));
    }

    public Slice<BookSummaryResponse> findBooks(List<Long> bookIds, Pageable pageable) {
        return repository.findAllById(bookIds, pageable)
                .map(book -> new BookSummaryResponse(
                        book.getId(),
                        book.getFullName(),
                        book.getAuthor(),
                        book.getPublicationYear(),
                        book.getPublisher()
                ));
    }

    public Slice<BookSummaryResponse> findBooks(String title, List<Long> bookIds, Pageable pageable) {
        return repository.findBooks(title, bookIds, pageable)
                .map(book -> new BookSummaryResponse(
                        book.getId(),
                        book.getFullName(),
                        book.getAuthor(),
                        book.getPublicationYear(),
                        book.getPublisher()
                ));
    }

    public BookDetailResponse find(long id){
        Book book = repository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new BookDetailResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn13(),
                book.getClassification(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getPublisher(),
                book.getVolumeName()
        );
    }


}
