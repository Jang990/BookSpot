package com.bookspot.stock.domain.service.loanable.exception;

import org.springframework.http.HttpStatusCode;

public class TooManyRequestsException extends ClientException {
    public TooManyRequestsException(HttpStatusCode statusCode) {
        super(statusCode);
    }
}