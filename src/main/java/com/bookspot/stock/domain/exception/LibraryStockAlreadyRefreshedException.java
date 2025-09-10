package com.bookspot.stock.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class LibraryStockAlreadyRefreshedException extends AppException {
    public LibraryStockAlreadyRefreshedException(long stockId) {
        super(
                "LIBRARY_STOCK_ALREADY_REFRESHED",
                "대출 현황(stockId=%d)은 이미 Refresh되었습니다.".formatted(stockId),
                "이미 Refresh된 대출 현황입니다.",
                HttpStatus.TOO_MANY_REQUESTS
        );
    }
}