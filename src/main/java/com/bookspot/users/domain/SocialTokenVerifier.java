package com.bookspot.users.domain;

public interface SocialTokenVerifier {
    SocialTokenDetail verifyToken(String idTokenString);
}
