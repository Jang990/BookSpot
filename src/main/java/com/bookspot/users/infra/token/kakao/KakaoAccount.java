package com.bookspot.users.infra.token.kakao;

public record KakaoAccount(
        boolean email_needs_agreement,
        boolean is_email_valid,
        boolean is_email_verified,
        String email
) { }