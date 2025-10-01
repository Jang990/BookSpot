package com.bookspot.users.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class UserShelfFullException extends AppException {
    public UserShelfFullException(Long userId) {
        super(
            "BOOK_SHELF_FULL",
            "사용자(%d)의 책장이 최대 사이즈를 초과했습니다.".formatted(userId),
            "이미 모든 책장을 사용하고 있습니다.",
            HttpStatus.CONFLICT
        );
    }
}
