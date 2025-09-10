package com.bookspot.users.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.users.domain.exception.UserBagEmptyException;
import com.bookspot.users.domain.exception.UserBagFullException;
import com.bookspot.users.domain.exception.UserNotFoundException;
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
        assertThrows(UserNotFoundException.class, () -> users.addBookToBag(book));
        assertThrows(UserNotFoundException.class, () -> users.deleteBookFromBag(book));
        assertThrows(UserNotFoundException.class, () -> users.clearBag());
    }

    @Test
    void 책가방_크기가_이미_최대라면_책을_추가할_수_없음() {
        // given
        Book book = mock(Book.class);
        Users users = new Users();
        ReflectionTestUtils.setField(users, "id", 123L);
        ReflectionTestUtils.setField(users, "bookBagSize", UsersConst.MAX_BAG_SIZE);

        // when, then
        assertThrows(UserBagFullException.class, () -> users.addBookToBag(book));
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

    @Test
    void 책가방_크기가_0이라면_책을_제거할_수_없음() {
        // given
        Book book = mock(Book.class);
        Users users = new Users();
        ReflectionTestUtils.setField(users, "id", 123L);
        ReflectionTestUtils.setField(users, "bookBagSize", 0);

        // when, then
        assertThrows(UserBagEmptyException.class, () -> users.deleteBookFromBag(book));
    }

    @Test
    void 책가방에_책을_제거하면_책가방_사이즈가_감소함() {
        // given
        Book book = mock(Book.class);
        when(book.getId()).thenReturn(1L);
        Users users = new Users();
        ReflectionTestUtils.setField(users, "id", 123L);
        ReflectionTestUtils.setField(users, "bookBagSize", 2);

        // when
        users.deleteBookFromBag(book);

        // then
        assertEquals(1, users.getBookBagSize());
    }

    @Test
    void 책가방_크기가_0이라면_clear_할_수_없음() {
        // given
        Users users = new Users();
        ReflectionTestUtils.setField(users, "id", 123L);
        ReflectionTestUtils.setField(users, "bookBagSize", 0);

        // when, then
        assertThrows(UserBagEmptyException.class, users::clearBag);
    }

    @Test
    void clearBag을_호출하면_bagSize가_0으로_초기화됨() {
        // given
        Users users = new Users();
        ReflectionTestUtils.setField(users, "id", 123L);
        ReflectionTestUtils.setField(users, "bookBagSize", 2);

        // when
        users.clearBag();

        // then
        assertEquals(0, users.getBookBagSize());
    }

}