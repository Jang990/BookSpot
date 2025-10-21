package com.bookspot.book.application;

import com.bookspot.book.application.dto.BookSearchDto;
import com.bookspot.book.application.mapper.BookDataMapper;
import com.bookspot.book.domain.exception.BookNotFoundException;
import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.infra.search.pagination.BookSortOptions;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import com.bookspot.book.infra.search.result.BookPageResult;
import com.bookspot.book.infra.BookSearchRepository;
import com.bookspot.book.infra.search.result.BookSearchAfterResult;
import com.bookspot.book.presentation.request.BookSearchAfterRequest;
import com.bookspot.book.presentation.request.BookSort;
import com.bookspot.book.presentation.response.*;
import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.category.domain.BookCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final BookSearchRepository bookSearchRepository;
    private final BookCategoryRepository bookCategoryRepository;

    public BookPreviewListResponse findAll(List<Long> bookIds) {
        List<Book> books = repository.findAllById(bookIds);
        if(books.size() != bookIds.size())
            throw new BookNotFoundException(bookIds);

        Map<Long, Book> bookMap = books.stream()
                .collect(Collectors.toMap(Book::getId, b -> b));

        // 전달받은 bookIds 순서와 일치시키기 위함. - 컨트롤러 로직인가?
        List<Book> sortedBooks = bookIds.stream()
                .map(bookMap::get)
                .toList();
        return new BookPreviewListResponse(
                sortedBooks.stream()
                        .map(book -> BookDataMapper.transform(book, bookCategoryRepository))
                        .toList()
        );
    }

    public BookPreviewPageResponse findBooks(BookSearchDto bookSearchDto, Pageable pageable) {
        BookPageResult pageResult = bookSearchRepository.search(
                BookDataMapper.transform(bookCategoryRepository, bookSearchDto),
                bookSearchDto.getSortBy() == BookSort.LOAN
                        ? OpenSearchPageable.sortByLoanCount(pageable)
                        : OpenSearchPageable.sortByRelevance(pageable)
        );

        return new BookPreviewPageResponse(
                pageResult.books().map(BookDataMapper::transform),
                pageResult.lastScore(),
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
                BookDataMapper.transform(bookCategoryRepository, bookSearchDto),
                new SearchAfterCond(
                        searchAfterCond.getLastScore(),
                        searchAfterCond.getLastLoanCount(),
                        searchAfterCond.getLastBookId(),
                        pageSize,
                        bookSearchDto.getSortBy() == BookSort.LOAN
                                ? BookSortOptions.SORT_BY_LOAN_COUNT
                                : BookSortOptions.SORT_BY_SCORE
                )
        );

        return new BookPreviewSearchAfterResponse(
                result.books().stream().map(BookDataMapper::transform).toList(),
                result.lastScore(),
                result.lastLoanCount(),
                result.lastBookId(),
                result.totalElements()
        );
    }

    public BookDetailResponse find(long id){
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

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
