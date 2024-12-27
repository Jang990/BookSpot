package com.bookspot.library;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LibraryStockDto {
    private String libraryName;
    private double distance;
    private int totalBooksRequested;
    private int availableBooksCount;
    private List<String> unavailableBooks;
}
