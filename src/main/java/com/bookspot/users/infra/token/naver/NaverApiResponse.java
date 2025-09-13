package com.bookspot.users.infra.token.naver;

record NaverApiResponse(
    String resultcode,
    String message,
    NaverUserInfo response
) { }