package com.bookspot.shelves.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class ShelfForbiddenException extends AppException {
    public ShelfForbiddenException(long userId, long shelfId) {
        super(
                "SHELF_FORBIDDEN",
                "사용자(%d)가 책장(%d)을 수정/삭제할 권한이 없습니다.".formatted(userId, shelfId),
                "책장을 수정/삭제할 권한이 없습니다.",
                HttpStatus.FORBIDDEN
        );
    }
}
