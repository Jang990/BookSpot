package com.bookspot.library.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class LibraryNotFoundException extends AppException {

    public LibraryNotFoundException(long libraryId) {
        super(
            "LIBRARY_NOT_FOUND",
            "도서관(%d)를 DB에서 찾을 수 없습니다.".formatted(libraryId),
            "도서관를 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
        );
    }

}