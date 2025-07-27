package com.bookspot.book.application;

import com.bookspot.book.application.dto.BookSearchDto;
import com.bookspot.book.application.mapper.BookDataMapper;
import com.bookspot.book.infra.search.result.BookPageResult;
import com.bookspot.book.infra.search.BookSearchRepository;
import com.bookspot.book.infra.search.result.BookSearchAfterResult;
import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import com.bookspot.book.presentation.response.BookDetailResponse;
import com.bookspot.book.presentation.response.BookPreviewPageResponse;
import com.bookspot.book.presentation.response.BookPreviewSearchAfterResponse;
import com.bookspot.book.presentation.response.BookResponse;
import com.bookspot.book.presentation.request.BookSearchRequest;
import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
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

    public BookPreviewPageResponse findBooks(BookSearchDto bookSearchDto, Pageable pageable) {
        BookPageResult pageResult = bookSearchRepository.search(
                BookDataMapper.transform(bookSearchDto),
                pageable
        );

        return new BookPreviewPageResponse(
                pageResult.books().map(BookDataMapper::transform),
                pageResult.lastLoanCount(),
                pageResult.lastBookId()
        );
    }

    public BookPreviewSearchAfterResponse findBooks(
            BookSearchDto bookSearchDto,
            BookSearchAfterRequest searchAfterCond,
            int pageSize
    ) {
        BookSearchAfterResult result = bookSearchRepository.search(
                BookDataMapper.transform(bookSearchDto),
                BookDataMapper.transform(searchAfterCond),
                pageSize
        );
        return new BookPreviewSearchAfterResponse(
                result.books().stream().map(BookDataMapper::transform).toList(),
                result.lastLoanCount(),
                result.lastBookId(),
                result.totalElements()
        );
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
