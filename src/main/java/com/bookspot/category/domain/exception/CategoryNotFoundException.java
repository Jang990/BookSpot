package com.bookspot.category.domain.exception;

import com.bookspot.global.error.AppException;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends AppException {

    public CategoryNotFoundException(long categoryId) {
        super(
            "CATEGORY_NOT_FOUND",
            "카테고리(%d)를 DB에서 찾을 수 없습니다.".formatted(categoryId),
            "카테고리를 찾을 수 없습니다.",
            HttpStatus.NOT_FOUND
        );
    }
}