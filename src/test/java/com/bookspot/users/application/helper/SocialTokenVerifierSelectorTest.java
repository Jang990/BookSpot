package com.bookspot.users.application.helper;

import com.bookspot.users.domain.OAuthProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SocialTokenVerifierSelectorTest {
    @Autowired
    SocialTokenVerifierSelector selector;

    @Test
    void OAuthProvider를_전부_지원해야함() {
        for (OAuthProvider provider : OAuthProvider.values()) {
            selector.select(provider);
        }
    }
}