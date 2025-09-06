package com.bookspot.users.domain;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class UsersTest {

    @Test
    void 책가방_사용_시_영속_객체만_사용가능() {
        Users users = new Users();
        assertThrows(IllegalStateException.class, () -> users.addBookToBag(1L));
    }

    @Test
    void 책가방_크기가_이미_최대라면_책을_추가할_수_없음() {
        Users users = new Users();
        ReflectionTestUtils.setField(users, "id", 123L);
        ReflectionTestUtils.setField(users, "bookBagSize", UsersConst.MAX_BAG_SIZE);
        assertThrows(IllegalStateException.class, () -> users.addBookToBag(1L));
    }

}