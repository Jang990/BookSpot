package com.bookspot.shelves.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ShelfNotFoundException extends AppException {

    public ShelfNotFoundException(long shelf) {
        super(
            "SHELF_FORBIDDEN",
            "책장(%d)를 DB에서 찾을 수 없습니다.".formatted(shelf),
            "책장을 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
        );
    }

    public ShelfNotFoundException(List<Long> shelfIds) {
        super(
                "SHELF_FORBIDDEN",
                "책장들(%s)을 DB에서 찾을 수 없습니다.".formatted(shelfIds),
                "책장을 찾을 수 없습니다.",
                HttpStatus.NOT_FOUND
        );
    }

    public ShelfNotFoundException() {
        super(
                "SHELF_NOT_FOUND",
                "책장을 찾을 수 없습니다.",
                HttpStatus.NOT_FOUND
        );
    }
}