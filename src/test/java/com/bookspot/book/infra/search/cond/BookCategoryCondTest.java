package com.bookspot.book.infra.search.cond;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookCategoryCondTest {

    @Test
    void leaf_카테고리_정보_생성() {
        BookCategoryCond categoryCond = BookCategoryCond.leaf(0, "총류");
        assertEquals(BookCategoryCond.LEAF_FIELD, categoryCond.getCategoryField());
        assertEquals("000.총류", categoryCond.getCategoryValue());
    }

    @Test
    void mid_카테고리_정보_생성() {
        BookCategoryCond categoryCond = BookCategoryCond.mid(120, "중간관리");
        assertEquals(BookCategoryCond.MID_FIELD, categoryCond.getCategoryField());
        assertEquals("120.중간관리", categoryCond.getCategoryValue());
    }


    @Test
    void top_카테고리_정보_생성() {
        BookCategoryCond categoryCond = BookCategoryCond.top(400, "자연과학");
        assertEquals(BookCategoryCond.TOP_FILED, categoryCond.getCategoryField());
        assertEquals("400.자연과학", categoryCond.getCategoryValue());
    }

}