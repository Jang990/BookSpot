package com.bookspot.users.presentation;

public record UserTokenResponse(
        String nickname,
        String accessToken,
        String role
) {
}
