package com.bookspot.stock.application.query;

import com.bookspot.stock.presentation.response.LibraryStockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StockQueryService {
    private final AvailableBookIdFinder availableBookIdFinder;

    public List<LibraryStockResponse> findLibraryStock(List<Long> libraryIds, List<Long> bookIds) {
        return libraryIds.stream().map(libraryId -> {
            List<Long> availableBookIds = availableBookIdFinder.find(libraryId, bookIds);
            return new LibraryStockResponse(
                    libraryId,
                    availableBookIds,
                    unavailableBookIds(bookIds, availableBookIds)
            );
        }).toList();
    }

    private List<Long> unavailableBookIds(List<Long> bookIds, List<Long> availableBookIds) {
        return bookIds.stream()
                .filter(id -> !availableBookIds.contains(id))
                .toList();
    }
}
