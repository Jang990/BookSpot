package com.bookspot.stock.presentation;

import com.bookspot.global.log.BasicLog;
import com.bookspot.stock.application.query.StockQueryService;
import com.bookspot.stock.presentation.request.StockSearchRequest;
import com.bookspot.stock.presentation.response.LibraryStockListResponse;
import com.bookspot.stock.presentation.response.LibraryStockResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@BasicLog
@RestController
@RequiredArgsConstructor
public class StockSearchController {
    private final StockQueryService stockQueryService;

    @GetMapping("/api/libraries/stocks")
    public ResponseEntity<LibraryStockListResponse> findLibraryStock(
            @Valid StockSearchRequest request) {
        return ResponseEntity.ok(
                new LibraryStockListResponse(
                        stockQueryService.findLibraryStock(
                                request.getLibraryIds(),
                                request.getBookIds()
                        )
                )
        );
    }
}
