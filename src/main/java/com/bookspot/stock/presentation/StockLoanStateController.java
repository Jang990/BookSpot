package com.bookspot.stock.presentation;

import com.bookspot.book.domain.Book;
import com.bookspot.global.log.BasicLog;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.application.LibraryStockRefreshService;
import com.bookspot.stock.application.LibraryStockService;
import com.bookspot.stock.domain.LibraryStock;
import com.bookspot.stock.domain.LoanState;
import com.bookspot.stock.presentation.request.StockLoanStateSearchRequest;
import com.bookspot.stock.presentation.response.StockLoanStateResponse;
import com.bookspot.stock.presentation.response.StockLoanStateResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@BasicLog
@RestController
@RequiredArgsConstructor
public class StockLoanStateController {
    private final LibraryStockRefreshService refreshService;
    private final LibraryStockService libraryStockService;

    /**
     * @see com.bookspot.stock.domain.exception.LibraryNotSupportsLoanStatusException
     * @see com.bookspot.stock.domain.exception.LibraryStockAlreadyRefreshedException
     * @see com.bookspot.stock.domain.exception.LibraryStockMismatchException
     * @see com.bookspot.stock.domain.exception.LibraryStockNotFoundException
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/stocks/{stockId}/loan/refresh")
    public ResponseEntity<StockLoanStateResponse> refresh(
            @PathVariable("stockId") long stockId
    ) {
        return ResponseEntity.ok(refreshService.refreshLoanState(stockId));
    }

    /**
     * @see com.bookspot.stock.domain.exception.LibraryStockNotFoundException
     */
    @GetMapping("/api/libraries/{libraryId}/stocks/loan")
    public ResponseEntity<StockLoanStateResponseList> findCurrentLoanState(
            @PathVariable("libraryId") long libraryId,
            @Valid StockLoanStateSearchRequest request
    ) {
        return ResponseEntity.ok(
                libraryStockService.findLibraryBookStock(
                        libraryId, request.getBookIds()
                )
        );
    }
}
