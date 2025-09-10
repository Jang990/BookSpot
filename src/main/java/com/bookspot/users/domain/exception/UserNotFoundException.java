package com.bookspot.users.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AppException {

    public UserNotFoundException(long userId) {
        super(
            "USER_NOT_FOUND",
            "사용자(%d)를 DB에서 찾을 수 없습니다.".formatted(userId),
            "사용자를 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
        );
    }

    public UserNotFoundException() {
        super(
                "USER_NOT_FOUND",
                "사용자를 찾을 수 없습니다.",
                HttpStatus.NOT_FOUND
        );
    }
}