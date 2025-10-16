package com.bookspot.book.presentation.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookSearchAfterRequest {
    public static final String PARAM_LAST_LOAN_COUNT = "lastLoanCount";
    public static final String PARAM_LAST_BOOK_ID    = "lastBookId";
    public static final String PARAM_LAST_SCORE = "lastScore";

    public static final String IGNORE_PARAM_LAST_LOAN_COUNT = "!" + PARAM_LAST_LOAN_COUNT;
    public static final String IGNORE_PARAM_LAST_BOOK_ID = "!" + PARAM_LAST_BOOK_ID;

    @NotNull
    private String lastScore;
    @NotNull
    private Long lastLoanCount;
    @NotNull
    private Long lastBookId;
}
