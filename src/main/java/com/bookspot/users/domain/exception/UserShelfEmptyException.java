package com.bookspot.users.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class UserShelfEmptyException extends AppException {
    public UserShelfEmptyException(Long userId) {
        super(
            "BOOK_SHELF_EMPTY",
            "ERROR - 사용자(%d)의 책장이 비어 있는데 제거를 시도했습니다.".formatted(userId),
            "책장 오류가 발생했습니다. 관리자에게 문의해주세요.",
            HttpStatus.CONFLICT
        );
    }
}