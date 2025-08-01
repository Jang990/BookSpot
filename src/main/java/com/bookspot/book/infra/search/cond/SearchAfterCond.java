package com.bookspot.book.infra.search.cond;

public record SearchAfterCond(Double lastScore, long lastLoanCount,long lastBookId) {
}
