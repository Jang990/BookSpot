package com.bookspot.users.presentation;

import com.bookspot.global.auth.JwtProvider;
import lombok.Getter;

@Getter
public class UserTokenResponse {
    private final String nickname;
    private final String accessToken;
    private final String role;
    private final long expiresInSecond;

    public UserTokenResponse(String nickname, String accessToken, String role) {
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.role = role;
        this.expiresInSecond = JwtProvider.validityMs / 1000;
    }
}
