package com.bookspot.stock;

import lombok.Data;

import java.util.List;

@Data
public class LibraryStockDto {
    private String libraryName;
    private int distance; // 미터 단위
    private int totalBooksRequested;
    private int availableBooksCount;
    private List<String> unavailableBooks;

    public LibraryStockDto(
            String libraryName, double distance,
            int totalBooksRequested, int availableBooksCount,
            List<String> unavailableBooks) {
        this.libraryName = libraryName;
        this.distance = (int) Math.round(distance);
        this.totalBooksRequested = totalBooksRequested;
        this.availableBooksCount = availableBooksCount;
        this.unavailableBooks = unavailableBooks;
    }
}
