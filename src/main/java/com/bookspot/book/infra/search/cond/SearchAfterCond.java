package com.bookspot.book.infra.search.cond;

public record SearchAfterCond(String lastScore, long lastLoanCount, long lastBookId, int pageSize) {
}
