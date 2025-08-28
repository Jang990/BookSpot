package com.bookspot.stock.domain.service.loanable.exception;

public class LoanStateApiException extends RuntimeException {
    public LoanStateApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanStateApiException(String message) {
        super(message);
    }

    public boolean isRetryable() {
        return false;
    }
}
