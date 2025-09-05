package com.bookspot.global.auth;

import com.bookspot.global.DateHolder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    protected static final long validityMs = 1000 * 60 * 60; // 1시간
    private final Key key;
    private final DateHolder dateHolder;

    public JwtProvider(@Value("${jwt.secret}") String secret, DateHolder dateHolder) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.dateHolder = dateHolder;
    }

    public String createToken(String email, String provider) {
        Date now = dateHolder.nowDate();
        Date expiry = new Date(now.getTime() + validityMs);

        return Jwts.builder()
//                .setSubject(userId) // TODO: DB 연동 후.
                .claim("email", email)
                .claim("provider", provider)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 만료, 빈 토큰, 서명 검증 실패, 잘못된 토큰 구조
            return false;
        }
    }

}
