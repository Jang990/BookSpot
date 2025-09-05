package com.bookspot.global.auth;

import com.bookspot.global.DateHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {
    @Mock
    private DateHolder dateHolder;

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        String secret = "0123456789abcdef0123456789abcdef";
        jwtProvider = new JwtProvider(secret, dateHolder);
    }

    @Test
    void 성공적인_토큰_검증() {
        when(dateHolder.nowDate()).thenReturn(new Date());

        String token = jwtProvider.createToken("test@example.com", "google");
        assertTrue(jwtProvider.validateToken(token));
    }

    @Test
    void 잘못된_토큰_검증() {
        assertFalse(jwtProvider.validateToken("Invalid.Token"));
    }

    @Test
    void 만료된_토큰_검증() {
        Date past = new Date(System.currentTimeMillis() - JwtProvider.validityMs * 2);
        when(dateHolder.nowDate()).thenReturn(past);

        String token = jwtProvider.createToken("test@example.com", "google");
        assertFalse(jwtProvider.validateToken(token));
    }
}