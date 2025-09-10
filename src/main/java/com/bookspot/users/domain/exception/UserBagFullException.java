package com.bookspot.users.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class UserBagFullException extends AppException {
    public UserBagFullException(Long userId) {
        super(
            "BOOK_BAG_FULL",
            "사용자(%d)의 책가방이 최대 사이즈를 초과했습니다.".formatted(userId),
            "책가방이 가득 찼습니다.",
            HttpStatus.CONFLICT
        );
    }
}
