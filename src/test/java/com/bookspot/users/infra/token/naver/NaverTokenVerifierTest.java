package com.bookspot.users.infra.token.naver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class NaverTokenVerifierTest {
    @Autowired
    NaverTokenVerifier verifier;

//    @Test
    void test() {
        assertThrows(RuntimeException.class, () -> verifier.verifyToken("ABCDE"));
    }

}