package com.bookspot.users.application.helper;

import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.SocialTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SocialTokenVerifierSelector {
    private final List<SocialTokenVerifier> socialTokenVerifierList;

    public SocialTokenVerifier select(OAuthProvider oAuthProvider) {
        return socialTokenVerifierList.stream()
                .filter(socialTokenVerifier -> socialTokenVerifier.supports(oAuthProvider))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 OAuth"));
    }
}
