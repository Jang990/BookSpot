package com.bookspot.book.infra.search.cond;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YearRangeTest {

    @Test
    void 시작연도가_끝연도보다_크면_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> new YearRange(2020, 10));
    }

    @Test
    void 연도중_하나라도_0보다_작으면_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> new YearRange(-1, 2025));
        assertThrows(IllegalArgumentException.class, () -> new YearRange(2025, -1));
    }

}