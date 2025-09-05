package com.bookspot.users.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OAuthProviderTest {

    @Test
    void 스트링값을_enum으로_변경해줌() {
        assertEquals(OAuthProvider.GOOGLE, OAuthProvider.fromString("google"));
        assertEquals(OAuthProvider.GOOGLE, OAuthProvider.fromString("Google"));
        assertEquals(OAuthProvider.GOOGLE, OAuthProvider.fromString("GOOGLE"));
    }

    @Test
    void 잘못된_값이_들어가면_예외를_던짐() {
        assertThrows(IllegalArgumentException.class, () -> OAuthProvider.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> OAuthProvider.fromString("aaa"));
        assertThrows(IllegalArgumentException.class, () -> OAuthProvider.fromString("ABC"));
        assertThrows(IllegalArgumentException.class, () -> OAuthProvider.fromString("NULL"));
    }

}