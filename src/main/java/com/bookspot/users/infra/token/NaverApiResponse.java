package com.bookspot.users.infra.token;

record NaverApiResponse(
    String resultcode,
    String message,
    NaverUserInfo response
) { }