package com.bookspot.stock.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class LibraryStockMismatchException extends AppException {
    public LibraryStockMismatchException(long stockId, long bookId, long libraryId) {
        super(
                "LIBRARY_STOCK_MISMATCH",
                "stock(%d)과 book(%d)/library(%d)가 일치하지 않습니다.".formatted(stockId, bookId, libraryId),
                "대출 현황 요청 정보가 일치하지 않습니다.",
                HttpStatus.BAD_REQUEST
        );
    }
}