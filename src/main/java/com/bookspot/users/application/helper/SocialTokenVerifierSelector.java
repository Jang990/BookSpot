package com.bookspot.users.application.helper;

import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.SocialTokenVerifier;

import java.util.List;

public class SocialTokenVerifierSelector {
    public static SocialTokenVerifier select(List<SocialTokenVerifier> socialTokenVerifiers, OAuthProvider oAuthProvider) {
        return socialTokenVerifiers.stream()
                .filter(socialTokenVerifier -> socialTokenVerifier.supports(oAuthProvider))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 OAuth"));
    }
}
