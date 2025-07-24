package com.bookspot.book.application;

import com.bookspot.book.application.mapper.BookDataMapper;
import com.bookspot.book.infra.search.BookSearchRepository;
import com.bookspot.book.infra.search.BookSearchCond;
import com.bookspot.book.presentation.BookDetailResponse;
import com.bookspot.book.presentation.BookResponse;
import com.bookspot.book.presentation.BookSearchRequest;
import com.bookspot.book.presentation.BookSummaryResponse;
import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookSearchRepository bookSearchRepository;

    public List<BookResponse> findAll(List<Long> bookIds) {
        List<Book> books = repository.findAllById(bookIds);
        if(books.size() != bookIds.size())
            throw new IllegalArgumentException("찾을 수 없는 ID가 포함돼 있음");
        return books.stream()
                .map(book -> new BookResponse(book.getId(), book.getTitle()))
                .toList();
    }

    public Page<BookSummaryResponse> findBooks(BookSearchRequest bookSearchRequest, Pageable pageable) {
        return bookSearchRepository.search(
                        BookSearchCond.builder()
                                .keyword(bookSearchRequest.getTitle())
                                .bookIds(bookSearchRequest.getBookIds())
                                .libraryId(bookSearchRequest.getLibraryId())
                                .build(),
                        pageable
                )
                .map(BookDataMapper::transform);
    }

    public BookDetailResponse find(long id){
        Book book = repository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new BookDetailResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn13(),
                book.getSubjectCode(),
                book.getAuthor(),
                book.getPublicationYear(),
                book.getPublisher()
        );
    }


}
