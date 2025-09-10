package com.bookspot.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class AppException extends RuntimeException {
    private final String code;
    private final HttpStatus status;
    private final String clientMessage;

    /** 로그 메시지와 프론트 메시지를 다르게 사용 */
    protected AppException(String code, String message, String clientMessage, HttpStatus status) {
        super(message); // 로그/디버깅용 내부 메시지
        this.code = code;
        this.clientMessage = clientMessage;
        this.status = status;
    }

    /** 로그 메시지와 프론트 메시지를 동일하게 사용 */
    protected AppException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.clientMessage = message;
        this.status = status;
    }
}
