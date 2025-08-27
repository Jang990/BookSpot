package com.bookspot.stock.domain.service.loanable.exception;

import org.springframework.http.HttpStatusCode;

public class ApiClientException extends RuntimeException {
    private final HttpStatusCode statusCode;

    public ApiClientException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}