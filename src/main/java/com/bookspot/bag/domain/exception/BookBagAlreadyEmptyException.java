package com.bookspot.bag.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class BookBagAlreadyEmptyException extends AppException {
    public BookBagAlreadyEmptyException(long userId) {
        super(
            "BOOK_BAG_ALREADY_EMPTY",
            "사용자 %d이(가) 이미 비어 있는 가방을 비우려고 했습니다.".formatted(userId), // 로그용
            "책가방이 이미 비어 있습니다.",             // 프론트용
            HttpStatus.CONFLICT
        );
    }
}