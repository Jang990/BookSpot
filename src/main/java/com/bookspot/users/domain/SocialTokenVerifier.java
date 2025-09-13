package com.bookspot.users.domain;

public interface SocialTokenVerifier {
    SocialTokenDetail verifyToken(String idTokenString);
    boolean supports(OAuthProvider provider);
}
