package com.bookspot.stock.domain.service.loanable.exception;

import org.springframework.http.HttpStatusCode;

public class ClientException extends ApiClientException {
    public ClientException(HttpStatusCode statusCode) {
        super("API 클라이언트 오류. Status: " + statusCode.value(), statusCode);
    }
}