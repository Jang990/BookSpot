package com.bookspot.users.presentation;

import com.bookspot.global.auth.dto.GeneratedToken;
import lombok.Getter;

@Getter
public class UserTokenResponse {
    private final long userId;
    private final String nickname;
    private final String accessToken;
    private final String role;
    private final long expiredAt;

    public UserTokenResponse(long userId, String nickname, String role, GeneratedToken token) {
        this.userId = userId;
        this.nickname = nickname;
        this.accessToken = token.accessToken();
        this.role = role;
        this.expiredAt = token.expiredAt().getTime();
    }
}
