package com.bookspot.stock.application;

import com.bookspot.stock.domain.LibraryStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryStockService {
    private final LibraryStockRepository repository;
    public List<Long> findUnavailableBookIds(long libraryId, List<Long> bookIds) {
        List<Long> result = new LinkedList<>();

        for (Long bookId : bookIds) {
            if (!repository.existsByLibraryIdAndBookId(libraryId, bookId))
                result.add(bookId);
        }

        return result;
    }
}
