package com.bookspot.users.domain.auth;

import com.bookspot.users.domain.OAuthProvider;

public interface SocialTokenVerifier {
    SocialTokenDetail verifyToken(String idTokenString);
    boolean supports(OAuthProvider provider);
}
