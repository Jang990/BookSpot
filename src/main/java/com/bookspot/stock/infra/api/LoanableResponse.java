package com.bookspot.stock.infra.api;

import lombok.Getter;

@Getter
public class LoanableResponse {

    private Response response;

    @Getter
    public static class Response {
        private String error;
        private Request request;
        private Result result;
    }

    @Getter
    public static class Request {
        private String isbn13;
        private String libCode;
    }

    @Getter
    public static class Result {
        private String hasBook;
        private String loanAvailable;
    }
}
