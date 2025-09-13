package com.bookspot.users.domain.auth;

import java.util.Objects;

public record SocialTokenDetail(String subjectId, String email) {
    public SocialTokenDetail {
        Objects.requireNonNull(subjectId);
    }
}
