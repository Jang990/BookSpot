package com.bookspot.global.api;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RequestException extends RuntimeException {

    private final HttpStatusCode statusCode;

    public RequestException(HttpStatusCode statusCode) {
        super("API 요청 실패. Status Code: " + statusCode.value());
        this.statusCode = statusCode;
    }
}
