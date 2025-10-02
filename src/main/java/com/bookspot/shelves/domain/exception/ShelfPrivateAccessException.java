package com.bookspot.shelves.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class ShelfPrivateAccessException extends AppException {
    public ShelfPrivateAccessException(long userId, long shelfId) {
        super(
                "SHELF_PRIVATE_FORBIDDEN",
                "해당 사용자(%d)는 비공개 책장(%d)에 접근할 권한이 없습니다.".formatted(userId, shelfId),
                "비공개 책장에 접근할 권한이 없습니다.",
                HttpStatus.FORBIDDEN
        );
    }

    public ShelfPrivateAccessException(long shelfId) {
        super(
                "SHELF_PRIVATE_FORBIDDEN",
                "비로그인 사용자는 비공개 책장(%d)에 접근할 권한이 없습니다.".formatted(shelfId),
                "비공개 책장에 접근할 권한이 없습니다.",
                HttpStatus.FORBIDDEN
        );
    }
}
