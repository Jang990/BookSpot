package com.bookspot.stock.presentation.response;

import com.bookspot.book.presentation.BookResponse;
import com.bookspot.library.presentation.LibraryDistanceResponse;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class LibraryStockResponse {
    private final LibraryDistanceResponse library;
    private final int availableBooksCount;
    private final int unavailableBooksCount;
    private final List<BookStockResponse> bookStocks;

    public LibraryStockResponse(
            LibraryDistanceResponse library,
            List<BookResponse> availableBooks,
            List<BookResponse> unavailableBooks) {
        this.library = library;
        bookStocks = new LinkedList<>();

        availableBooksCount = availableBooks.size();
        unavailableBooksCount = unavailableBooks.size();
        addStocks(availableBooks, true);
        addStocks(unavailableBooks, false);
    }

    private void addStocks(List<BookResponse> unavailableBooks, boolean available) {
        bookStocks.addAll(unavailableBooks.stream()
                .map(book -> new BookStockResponse(book.getId(), book.getTitle(), available))
                .toList());
    }
}
