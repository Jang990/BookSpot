package com.bookspot.stock.presentation.response;

import com.bookspot.book.presentation.BookResponse;
import com.bookspot.library.LibraryDistanceResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LibraryStockResponse {

    private LibraryDistanceResponse library;
    private int totalBooksCount;
    private List<BookResponse> availableBooks;
    private List<BookResponse> unavailableBooks;

    public LibraryStockResponse(
            LibraryDistanceResponse library,
            List<BookResponse> availableBooks,
            List<BookResponse> unavailableBooks) {
        this.library = library;
        this.availableBooks = availableBooks;
        this.unavailableBooks = unavailableBooks;
        totalBooksCount = availableBooks.size() + unavailableBooks.size();
    }
}
