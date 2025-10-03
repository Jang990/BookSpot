package com.bookspot.shelfbooks.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ShelfBookAlreadyExistsException extends AppException {
    public ShelfBookAlreadyExistsException(long shelfId, long bookId) {
        super(
                "SHELF_BOOK_ALREADY_EXISTS",
                "책장(%d)에 이미 책(%d)이 존재합니다.".formatted(shelfId, bookId),
                "이미 책장에 존재하는 책입니다.",
                HttpStatus.CONFLICT
        );
    }

    public ShelfBookAlreadyExistsException(List<Long> shelfIds, long bookId) {
        super(
                "SHELF_BOOK_ALREADY_EXISTS",
                "이미 책(%d)이 존재하는 책장(%s)가 있습니다.".formatted(bookId, shelfIds.toString()),
                "이미 책이 존재하는 책장이 있습니다.",
                HttpStatus.CONFLICT
        );
    }
}