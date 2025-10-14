package com.bookspot.shelfbooks.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ShelfBookNotFoundException extends AppException {
    public ShelfBookNotFoundException(long shelfId, long bookId) {
        super(
                "SHELF_BOOK_NOT_FOUND",
                "책장(%d)에 책(%d)을 찾을 수 없습니다.".formatted(shelfId, bookId),
                "책장에서 해당 책을 찾을 수 없습니다.",
                HttpStatus.NOT_FOUND
        );
    }

    public ShelfBookNotFoundException(List<Long> shelfIds, long bookId) {
        super(
                "SHELF_BOOK_NOT_FOUND",
                "책장(%s)들에서 책(%d)을 찾을 수 없습니다.".formatted(shelfIds, bookId),
                "책장들에서 해당 책을 찾을 수 없습니다.",
                HttpStatus.NOT_FOUND
        );
    }
}