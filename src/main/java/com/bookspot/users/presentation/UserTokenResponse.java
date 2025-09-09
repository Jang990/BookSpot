package com.bookspot.users.presentation;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.dto.GeneratedToken;
import lombok.Getter;

import java.util.Date;

@Getter
public class UserTokenResponse {
    private final String nickname;
    private final String accessToken;
    private final String role;
    private final long expiredAt;

    public UserTokenResponse(String nickname, String role, GeneratedToken token) {
        this.nickname = nickname;
        this.accessToken = token.accessToken();
        this.role = role;
        this.expiredAt = token.expiredAt().getTime();
    }
}
