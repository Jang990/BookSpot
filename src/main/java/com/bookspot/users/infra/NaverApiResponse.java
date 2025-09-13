package com.bookspot.users.infra;

record NaverApiResponse(
    String resultcode,
    String message,
    NaverUserInfo response
) { }