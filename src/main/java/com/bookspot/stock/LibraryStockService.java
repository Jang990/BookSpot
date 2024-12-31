package com.bookspot.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryStockService {
    public List<Long> findUnavailableBookIds(long libraryId, List<Long> bookIds) {
        if(libraryId == 1L)
            return List.of(1L, 2L, 3L, 4L);
        return List.of(2L, 3L);
    }
}
