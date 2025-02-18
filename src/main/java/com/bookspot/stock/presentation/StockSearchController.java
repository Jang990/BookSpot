package com.bookspot.stock.presentation;

import com.bookspot.book.application.BookService;
import com.bookspot.book.presentation.BookResponse;
import com.bookspot.library.LibraryDistanceResponse;
import com.bookspot.library.LibraryService;
import com.bookspot.stock.application.LibraryStockService;
import com.bookspot.stock.presentation.request.StockSearchRequest;
import com.bookspot.stock.presentation.response.LibraryStockResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class StockSearchController {
    private final LibraryService libraryService;
    private final LibraryStockService stockService;
    private final BookService bookService;

    @GetMapping("/api/libraries/stocks")
    public ResponseEntity<List<LibraryStockResponse>> findLibraryStock(
            @Valid StockSearchRequest request) {
        List<BookResponse> books = bookService.findAll(request.getBookIds());

        List<LibraryDistanceResponse> libraries =
                findLibrary(request.getLatitude(), request.getLongitude());
        if (libraries.isEmpty()) {
            return ResponseEntity.ok(List.of());
        }

        Map<Long, BookResponse> booksMap = books.stream()
                .collect(Collectors.toMap(BookResponse::getId, Function.identity()));
        return ResponseEntity.ok(
                libraries.stream().map(library -> {
                    List<Long> availableBookIds = stockService
                            .findAvailableBookIds(library.getLibraryId(), request.getBookIds());
                    List<Long> unavailableBookIds = request.getBookIds().stream()
                            .filter((id) -> !availableBookIds.contains(id)).toList();
                    return new LibraryStockResponse(
                            library,
                            matchBookInfo(booksMap, availableBookIds),
                            matchBookInfo(booksMap, unavailableBookIds)
                    );
                }).toList());
    }

    private List<BookResponse> matchBookInfo(Map<Long, BookResponse> booksMap, List<Long> bookIds) {
        return bookIds.stream().map(booksMap::get).toList();
    }

    private List<LibraryDistanceResponse> findLibrary(double latitude, double longitude) {
        return libraryService.findLibrariesWithin5km(latitude, longitude);
    }
}
