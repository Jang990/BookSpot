package com.bookspot.global.auth;

import com.bookspot.global.DateHolder;
import jakarta.servlet.ServletException;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JwtAuthenticationFilterTest {
    private JwtProvider jwtProvider;
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void beforeEach() {
        String secret = "0123456789abcdef0123456789abcdef";
        DateHolder dateHolder = new DateHolder();
        jwtProvider = new JwtProvider(secret, dateHolder);
        filter = new JwtAuthenticationFilter(jwtProvider);
        SecurityContextHolder.clearContext();
    }

    @Test
    void 토큰이_없다면_시큐리티_홀더_영향_없음() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilterInternal(request, response, (req, res) -> {});

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void 토큰이_있다면_시큐리티_홀더에_토큰정보가_저장됨() throws ServletException, IOException {
        String validToken = jwtProvider.createToken("test@example.com", "google");

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + validToken);

        filter.doFilterInternal(request, response, (req, res) -> {});

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals("test@example.com_google", authentication.getPrincipal());
    }

    @Test
    void 토큰이_invalid라면_시큐리티_홀더에_영향_없음() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer invalid.token");
        MockHttpServletResponse response = new MockHttpServletResponse();

        filter.doFilterInternal(request, response, (req, res) -> {});

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }


}