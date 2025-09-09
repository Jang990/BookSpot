package com.bookspot.users.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsersRoleTest {

    @Test
    void 스트링값을_enum으로_변경해줌() {
        assertEquals(UsersRole.USER, UsersRole.fromString("user"));
        assertEquals(UsersRole.USER, UsersRole.fromString("User"));
        assertEquals(UsersRole.USER, UsersRole.fromString("USER"));
        assertEquals(UsersRole.ADMIN, UsersRole.fromString("admin"));
        assertEquals(UsersRole.ADMIN, UsersRole.fromString("Admin"));
        assertEquals(UsersRole.ADMIN, UsersRole.fromString("ADMIN"));
    }

    @Test
    void 잘못된_값이_들어가면_예외를_던짐() {
        assertThrows(IllegalArgumentException.class, () -> UsersRole.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> UsersRole.fromString("aaa"));
        assertThrows(IllegalArgumentException.class, () -> UsersRole.fromString("ABC"));
        assertThrows(IllegalArgumentException.class, () -> UsersRole.fromString("NULL"));
    }

}