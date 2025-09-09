package com.bookspot.global.auth;

import com.bookspot.global.DateHolder;
import com.bookspot.global.auth.dto.GeneratedToken;
import com.bookspot.users.application.UserDto;
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
    private final Date baseDate = new Date();

    @BeforeEach
    void setUp() {
        String secret = "0123456789abcdef0123456789abcdef";
        jwtProvider = new JwtProvider(secret, dateHolder);
    }

    @Test
    void 성공적인_토큰_검증() {
        GeneratedToken token = createValidToken(1L, "user");
        assertTrue(jwtProvider.validateToken(token.accessToken()));
        assertEquals(
                baseDate.getTime() + JwtProvider.validityMs,
                token.expiredAt().getTime()
        );
    }

    @Test
    void 잘못된_토큰_검증() {
        assertFalse(jwtProvider.validateToken("Invalid.Token"));
    }

    @Test
    void 만료된_토큰_검증() {
        Date past = new Date(baseDate.getTime() - JwtProvider.validityMs * 2);
        when(dateHolder.nowDate()).thenReturn(past);

        UserDto dummyUser = new UserDto(1L, "dummy-email", "dummy-nickname", "user");
        String token = jwtProvider.createToken(dummyUser).accessToken();
        assertFalse(jwtProvider.validateToken(token));
    }

    @Test
    void 토큰_상세정보_조회_가능__ROLE은_대문자에_prefix가_붙음() {
        String token = createValidToken(123L, "user").accessToken();

        Claims claims = jwtProvider.getClaims(token);

        assertEquals("123", claims.getSubject());
        assertEquals("ROLE_USER", claims.get(JwtProvider.ROLE_KEY));
    }

    private GeneratedToken createValidToken(long userId, String role) {
        when(dateHolder.nowDate()).thenReturn(baseDate);
        return jwtProvider.createToken(new UserDto(userId, "dummy-email", "dummy-nickname", role));
    }
}