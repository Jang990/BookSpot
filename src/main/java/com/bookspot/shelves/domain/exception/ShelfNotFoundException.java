package com.bookspot.shelves.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class ShelfNotFoundException extends AppException {

    public ShelfNotFoundException(long shelf) {
        super(
            "SHELF_FORBIDDEN",
            "책장(%d)를 DB에서 찾을 수 없습니다.".formatted(shelf),
            "책장을 찾을 수 없습니다.",
            HttpStatus.FORBIDDEN
        );
    }

    public ShelfNotFoundException() {
        super(
                "SHELF_NOT_FOUND",
                "책장을 찾을 수 없습니다.",
                HttpStatus.FORBIDDEN
        );
    }
}