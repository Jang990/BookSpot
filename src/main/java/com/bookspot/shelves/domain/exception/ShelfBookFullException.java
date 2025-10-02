package com.bookspot.shelves.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class ShelfBookFullException extends AppException {
    public ShelfBookFullException(long shelfId, long bookId) {
        super(
                "SHELF_BOOKS_FULL",
                "책장(%d)의 최대 허용 개수를 초과해서 책(%d)을 넣을 수 없습니다.".formatted(shelfId, bookId),
                "책장이 가득 차서 더 이상 책을 추가할 수 없습니다.",
                HttpStatus.CONFLICT
        );
    }
}