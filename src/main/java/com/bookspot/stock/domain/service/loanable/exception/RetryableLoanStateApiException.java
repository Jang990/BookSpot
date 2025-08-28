package com.bookspot.stock.domain.service.loanable.exception;

public class RetryableLoanStateApiException extends LoanStateApiException{
    public RetryableLoanStateApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryableLoanStateApiException(String message) {
        super(message);
    }

    @Override
    public boolean isRetryable() {
        return true;
    }
}
