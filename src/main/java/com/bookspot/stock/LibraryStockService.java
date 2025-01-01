package com.bookspot.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryStockService {
    public List<Long> findUnavailableBookIds(long libraryId, List<Long> bookIds) {
        if(libraryId % 2 == 1)
            return List.of(libraryId, 1L, 2L, 3L);
        return List.of(libraryId, 2L, 3L);
    }
}
