package com.bookspot.library;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryService {
    public List<LibraryDistanceDto> findLibrariesWithin5km(double latitude, double longitude) {
        return List.of(
                new LibraryDistanceDto(1L, "A도서관", 3.41),
                new LibraryDistanceDto(2L, "B도서관", 4.41)
        );
    }
}