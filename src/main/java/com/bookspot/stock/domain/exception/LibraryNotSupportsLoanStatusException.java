package com.bookspot.stock.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class LibraryNotSupportsLoanStatusException extends AppException {
    public LibraryNotSupportsLoanStatusException(long libraryId) {
        super(
            "LIBRARY_NOT_SUPPORTS_LOAN_STATUS",
            "도서관(%d)은 대출 현황을 지원하지 않습니다.".formatted(libraryId),
            "대출 현황을 지원하지 않는 도서관입니다.",
            HttpStatus.UNPROCESSABLE_ENTITY
        );
    }
}