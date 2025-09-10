package com.bookspot.bag.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class BookAlreadyRemovedFromBagException extends AppException {
    public BookAlreadyRemovedFromBagException(long userId, long bookId) {
        super(
                "BOOK_ALREADY_REMOVED_FROM_BAG",
                "사용자(%d)가 가방에 존재하지 않는 책(%d)을 제거하려 시도했습니다.".formatted(userId, bookId), // 로그용
                "가방에 존재하지 않는 책은 제거할 수 없습니다.",     // 프론트용
                HttpStatus.CONFLICT
        );
    }
}