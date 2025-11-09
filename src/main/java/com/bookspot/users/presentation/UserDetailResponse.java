package com.bookspot.users.presentation;

import java.time.LocalDateTime;

public record UserDetailResponse(String email, String provider, String createdAt) {
    public UserDetailResponse(String email, String provider, LocalDateTime createdAt) {
        this(email, provider, createdAt.toString());
    }
}
