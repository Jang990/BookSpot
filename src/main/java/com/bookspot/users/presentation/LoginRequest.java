package com.bookspot.users.presentation;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String idToken) {
}
