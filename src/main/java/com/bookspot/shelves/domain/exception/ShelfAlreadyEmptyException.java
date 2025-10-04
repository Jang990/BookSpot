package com.bookspot.shelves.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class ShelfAlreadyEmptyException extends AppException {

    public ShelfAlreadyEmptyException(long shelfId) {
        super(
                "SHELF_ALREADY_EMPTY", // 에러 코드
                "책장(%d)이 이미 비어있어 작업을 수행할 수 없습니다.".formatted(shelfId), // 개발자용 상세 메시지 (로그용)
                "책장이 비어있어 작업을 수행할 수 없습니다.", // 사용자용 메시지
                HttpStatus.CONFLICT
        );
    }
}