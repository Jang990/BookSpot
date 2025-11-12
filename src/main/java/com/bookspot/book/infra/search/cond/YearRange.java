package com.bookspot.book.infra.search.cond;

public record YearRange(int startYear, int endYear) {
    public YearRange {
        if(startYear < 0 || endYear < 0 || startYear > endYear)
            throw new IllegalArgumentException();
    }
}
