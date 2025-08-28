package com.bookspot.stock.presentation;

import com.bookspot.global.log.BasicLog;
import com.bookspot.stock.application.LibraryStockRefreshService;
import com.bookspot.stock.application.LibraryStockService;
import com.bookspot.stock.presentation.request.StockLoanStateSearchRequest;
import com.bookspot.stock.presentation.response.StockLoanStateResponse;
import com.bookspot.stock.presentation.response.StockLoanStateResponseList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/stocks/{stockId}/loan/refresh")
    public ResponseEntity<StockLoanStateResponse> refresh(
            @PathVariable("stockId") long stockId
    ) {
        return ResponseEntity.ok(refreshService.refreshLoanState(stockId));
    }

    @GetMapping("/api/stocks/loan")
    public ResponseEntity<StockLoanStateResponseList> findCurrentLoanState(
            @Valid StockLoanStateSearchRequest request
    ) {
        return ResponseEntity.ok(
                libraryStockService.findCurrentLoanState(
                        request.getStockIds()
                )
        );
    }
}
