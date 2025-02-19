package com.bookspot.stock.application.query;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.book.presentation.BookResponse;
import com.bookspot.library.LibraryDistanceResponse;
import com.bookspot.library.infra.LibraryRepositoryForView;
import com.bookspot.stock.domain.LibraryStockRepository;
import com.bookspot.stock.presentation.response.LibraryStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockQueryService {
    private final BookRepository bookRepository;
    private final LibraryRepositoryForView libraryRepository;
    private final AvailableBookIdFinder availableBookIdFinder;

    // TODO: 한방 쿼리?
    // TODO: Page<T>으로 변경
    public List<LibraryStockResponse> findLibraryStockIn5km(List<Long> bookIds, Location location) {
        List<Book> books = bookRepository.findAllById(bookIds);
        if(books.size() != bookIds.size())
            throw new IllegalArgumentException(); // TODO: 커스텀 예외 필요

        List<LibraryDistanceResponse> libraries = findLibraryIn5Km(location);
        if(libraries.isEmpty())
            return List.of();

        Map<Long, Book> booksMap = books.stream()
                .collect(Collectors.toMap(
                        Book::getId, Function.identity()));

        return libraries.stream().map(library -> {
            List<Long> availableBookIds = availableBookIdFinder.find(library.getLibraryId(), bookIds);
            return aggregate(bookIds, library, availableBookIds, booksMap);
        }).toList();
    }

    private LibraryStockResponse aggregate(
            List<Long> bookIds,
            LibraryDistanceResponse library,
            List<Long> availableBookIds,
            Map<Long, Book> booksMap) {
        List<Long> unavailableBookIds = bookIds.stream()
                .filter((id) -> !availableBookIds.contains(id)).toList();
        return new LibraryStockResponse(
                library,
                matchBookInfo(booksMap, availableBookIds),
                matchBookInfo(booksMap, unavailableBookIds)
        );
    }

    private List<BookResponse> matchBookInfo(Map<Long, Book> booksMap, List<Long> bookIds) {
        return bookIds.stream().map(booksMap::get)
                .map(book -> new BookResponse(book.getId(), book.getTitle()))
                .toList();
    }

    private List<LibraryDistanceResponse> findLibraryIn5Km(Location location) {
        return  libraryRepository.findLibrariesWithinRadius(
                location.latitude(),
                location.longitude(),
                PageRequest.of(0, 10)
        ).getContent();
    }
}
