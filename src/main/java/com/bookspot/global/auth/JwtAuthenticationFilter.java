package com.bookspot.global.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = resolveToken(request);
        System.out.println("토큰 확인 ===> " + token);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("유효 토큰? ===>" + jwtProvider.validateToken(token));
        Claims claims = jwtProvider.getClaims(token);
        System.out.println("이메일 정보 ===> " + claims.get(JwtProvider.EMAIL_KEY));
        System.out.println("제공자 정보 ===> " + claims.get(JwtProvider.PROVIDER_KEY));
        filterChain.doFilter(request, response);
    }

    public static String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null) return null;
        if (!header.startsWith("Bearer ")) return null;
        String token = header.substring(7).trim();
        return token.isEmpty() ? null : token;
    }
}
