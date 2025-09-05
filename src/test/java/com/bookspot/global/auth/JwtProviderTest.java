package com.bookspot.global.auth;

import com.bookspot.global.DateHolder;
import io.jsonwebtoken.Claims;
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
        String token = createValidToken("test@example.com", "google");
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

        String token = jwtProvider.createToken("test2@example.com", "googleasd");
        assertFalse(jwtProvider.validateToken(token));
    }

    @Test
    void 토큰_상세정보_조회_가능() {
        String token = createValidToken("ABC@example.com", "google");

        Claims claims = jwtProvider.getClaims(token);

        assertEquals("ABC@example.com", claims.get(JwtProvider.EMAIL_KEY));
        assertEquals("google", claims.get(JwtProvider.PROVIDER_KEY));
    }

    private String createValidToken(String email, String provider) {
        when(dateHolder.nowDate()).thenReturn(new Date());
        return jwtProvider.createToken(email, provider);
    }
}