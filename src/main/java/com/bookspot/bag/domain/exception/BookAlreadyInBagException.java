package com.bookspot.bag.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class BookAlreadyInBagException extends AppException {
    public BookAlreadyInBagException(long userId, long bookId) {
        super(
            "BOOK_ALREADY_IN_BAG",
            "사용자(%d)의 가방에 이미 존재하는 책(%d)을 생성하려고 했습니다.".formatted(userId, bookId), // 로그용
            "이미 책가방에 있는 책입니다.", // 프론트용
            HttpStatus.CONFLICT
        );
    }
}