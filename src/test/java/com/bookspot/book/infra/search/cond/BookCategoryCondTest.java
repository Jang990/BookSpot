package com.bookspot.book.infra.search.cond;

import com.bookspot.category.application.BookCategoryDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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


    @ParameterizedTest(name = "{0}")
    @MethodSource("args")
    void 카테고리_이름_생성(String expected, int id, String name) {
        assertEquals(expected, BookCategoryCond.buildValue(id, name));
    }

    static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of("000.총류", 0, "총류"),
                Arguments.of("010.도서학, 서지학", 10, "도서학, 서지학"),
                Arguments.of("011.저작", 11, "저작"),
                Arguments.of("376.중등교육", 376, "중등교육")
        );
    }

}