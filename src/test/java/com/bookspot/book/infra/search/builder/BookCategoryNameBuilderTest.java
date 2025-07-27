package com.bookspot.book.infra.search.builder;

import com.bookspot.category.application.BookCategoryDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BookCategoryNameBuilderTest {
    BookCategoryNameBuilder builder = new BookCategoryNameBuilder();

    @ParameterizedTest(name = "{0}")
    @MethodSource("args")
    void 카테고리_이름_생성(String expected, BookCategoryDto dto) {
        assertEquals(expected, builder.build(dto));
    }

    static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of("000.총류", new BookCategoryDto(0, "총류")),
                Arguments.of("010.도서학, 서지학", new BookCategoryDto(10, "도서학, 서지학")),
                Arguments.of("011.저작", new BookCategoryDto(11, "저작")),
                Arguments.of("376.중등교육", new BookCategoryDto(376, "중등교육"))
        );
    }

}