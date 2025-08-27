package com.bookspot.stock.domain.service.loanable.exception;

import org.springframework.http.HttpStatusCode;

public class ServerException extends ApiClientException {
    public ServerException(HttpStatusCode statusCode) {
        super("API 서버 오류. Status: " + statusCode.value(), statusCode);
    }
}