package com.bookspot.users.infra.token.kakao;

// 카카오 사용자 정보 응답
public record KakaoUserInfo(
        Long id,
        String connected_at,
        KakaoAccount kakao_account
) { }

