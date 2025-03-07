package com.bookspot.stock.presentation;

import com.bookspot.stock.application.query.Location;
import com.bookspot.stock.application.query.StockQueryService;
import com.bookspot.stock.presentation.request.StockSearchRequest;
import com.bookspot.stock.presentation.response.LibraryStockResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockSearchController {
    private final StockQueryService stockQueryService;

    @GetMapping("/api/libraries/stocks")
    public ResponseEntity<String> findLibraryStock(
            @Valid StockSearchRequest request) {
        System.out.println(request);

        // TODO: mbr 박스 만들고 도서관 검색 + 재고 검색
        return ResponseEntity.ok("Hello World");
    }
}
