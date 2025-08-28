package com.bookspot.stock.presentation;

import com.bookspot.global.log.BasicLog;
import com.bookspot.stock.application.LibraryStockRefreshService;
import com.bookspot.stock.presentation.response.StockLoanStateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@BasicLog
@RestController
@RequiredArgsConstructor
public class StockLoanStateController {
    private final LibraryStockRefreshService refreshService;

    @PostMapping("/api/stocks/{stockId}/loan/refresh")
    public ResponseEntity<StockLoanStateResponse> refresh(
            @PathVariable("stockId") long stockId
    ) {
        return ResponseEntity.ok(refreshService.refreshLoanState(stockId));
    }
}
