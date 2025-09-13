package com.bookspot.users.infra;

import com.fasterxml.jackson.annotation.JsonProperty;

record NaverUserInfo(
    String id,
    String nickname,
    String name,
    String email,
    String gender,
    String age,
    String birthday,
    @JsonProperty("profile_image") String profileImage,
    String birthyear,
    String mobile
) { }