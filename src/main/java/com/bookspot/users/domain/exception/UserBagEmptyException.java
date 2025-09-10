package com.bookspot.users.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class UserBagEmptyException extends AppException {
    public UserBagEmptyException(Long userId) {
        super(
            "BOOK_BAG_EMPTY",
            "사용자(%d)의 책가방이 비어 있어 수행할 수 없습니다.".formatted(userId),
            "책가방이 이미 비어 있습니다.",
            HttpStatus.CONFLICT
        );
    }
}