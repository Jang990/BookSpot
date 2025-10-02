package com.bookspot.shelfbooks.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class ShelfBookAlreadyExistsException extends AppException {
    public ShelfBookAlreadyExistsException(long shelfId, long bookId) {
        super(
                "SHELF_BOOK_ALREADY_EXISTS",
                "책장(%d)에 이미 책(%d)이 존재합니다.".formatted(shelfId, bookId),
                "이미 책장에 존재하는 책입니다.",
                HttpStatus.CONFLICT
        );
    }
}