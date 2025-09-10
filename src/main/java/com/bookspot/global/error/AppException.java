package com.bookspot.global.error;

import org.springframework.http.HttpStatus;

public abstract class AppException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    protected AppException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public String getCode() { return code; }
    public HttpStatus getStatus() { return status; }
}
