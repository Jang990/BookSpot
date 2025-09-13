package com.bookspot.users.infra.token.kakao;

// 카카오 토큰 정보 응답
public record KakaoTokenInfo(
        Long id,
        Integer expires_in,
        Integer app_id
) { }

