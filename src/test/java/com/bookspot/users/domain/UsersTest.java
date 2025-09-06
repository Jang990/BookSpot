package com.bookspot.users.domain;

import com.bookspot.book.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersTest {

    @Test
    void 책가방_사용_시_영속_객체만_사용가능() {
        // given
        Book book = mock(Book.class);
        Users users = new Users();

        // when, then
        assertThrows(IllegalStateException.class, () -> users.addBookToBag(book));
    }

    @Test
    void 책가방_크기가_이미_최대라면_책을_추가할_수_없음() {
        // given
        Book book = mock(Book.class);
        Users users = new Users();
        ReflectionTestUtils.setField(users, "id", 123L);
        ReflectionTestUtils.setField(users, "bookBagSize", UsersConst.MAX_BAG_SIZE);

        // when, then
        assertThrows(IllegalStateException.class, () -> users.addBookToBag(book));
    }

    @Test
    void 책가방에_책을_추가하면_책가방_사이즈가_증가함() {
        // given
        Book book = mock(Book.class);
        when(book.getId()).thenReturn(1L);
        Users users = new Users();
        ReflectionTestUtils.setField(users, "id", 123L);

        // when
        users.addBookToBag(book);

        // then
        assertEquals(1, users.getBookBagSize());
    }

}