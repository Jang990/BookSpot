package com.bookspot.book.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class BookNotFoundException extends AppException {

    public BookNotFoundException(long bookId) {
        super(
            "BOOK_NOT_FOUND",
            "책(%d)을 DB에서 찾을 수 없습니다.".formatted(bookId),
            "책을 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
        );
    }

    public BookNotFoundException() {
        super(
                "BOOK_NOT_FOUND",
                "책을 찾을 수 없습니다.",
                HttpStatus.NOT_FOUND
        );
    }
}