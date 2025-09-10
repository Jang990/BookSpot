package com.bookspot.book.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class BookNotFoundException extends AppException {

    public BookNotFoundException(long bookId) {
        super(
            "BOOK_NOT_FOUND",
            "책(%d)을 DB에서 찾을 수 없습니다.".formatted(bookId),
            "책을 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
        );
    }

    public BookNotFoundException(List<Long> bookIds) {
        super(
                "BOOK_NOT_FOUND",
                "DB에서 찾을 수 없는 책(%s)이 포함돼 있습니다.".formatted(bookIds),
                "찾을 수 없는 책이 포함돼 있습니다.",
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