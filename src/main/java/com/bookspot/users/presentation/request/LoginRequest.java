package com.bookspot.users.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String idToken) {
}
