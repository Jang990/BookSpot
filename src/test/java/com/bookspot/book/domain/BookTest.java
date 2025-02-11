package com.bookspot.book.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void 볼륨명이_존재하면_풀네임에서_합쳐서_보여줌() {
        Book book = new Book();
        ReflectionTestUtils.setField(book, "title", "객체지향의 사실과 오해");
        ReflectionTestUtils.setField(book, "volumeName", "2권");

        assertEquals("객체지향의 사실과 오해 (2권)", book.getFullName());
    }

    @ParameterizedTest
    @CsvSource({
            "'Effective Java', 'Effective Java', ", // null
            "'Clean Code', 'Clean Code', ''" // blank
    })
    void 볼륨명이_존재하지_않으면_풀네임에_타이틀만_보여준다(String expected, String title, String volumeName) {
        Book blankVolume = new Book();
        ReflectionTestUtils.setField(blankVolume, "title", title);
        ReflectionTestUtils.setField(blankVolume, "volumeName", volumeName);
        assertEquals(expected, blankVolume.getFullName());
    }
}