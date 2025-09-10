package com.bookspot.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
    
    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppErrorResponse> handleAppException(AppException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new AppErrorResponse(ex.getCode(), ex.getClientMessage()));
    }

}
