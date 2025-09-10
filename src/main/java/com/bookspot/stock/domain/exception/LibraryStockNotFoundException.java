package com.bookspot.stock.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class LibraryStockNotFoundException extends AppException {

    public LibraryStockNotFoundException(long stockId) {
        super(
            "STOCK_NOT_FOUND",
            "도서관 재고(%d)를 DB에서 찾을 수 없습니다.".formatted(stockId),
            "도서관 도서 정보를 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
        );
    }

    public LibraryStockNotFoundException(long libraryId, List<Long> bookIds) {
        super(
                "STOCK_NOT_FOUND",
                "도서관(%d)에서 책(%s) 중 하나를 DB에서 찾을 수 없습니다.".formatted(libraryId, bookIds.toString()),
                "도서관 도서 정보를 찾을 수 없습니다.",
                HttpStatus.NOT_FOUND
        );
    }

}