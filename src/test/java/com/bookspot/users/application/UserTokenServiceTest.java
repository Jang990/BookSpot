package com.bookspot.users.application;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.dto.GeneratedToken;
import com.bookspot.users.infra.GoogleTokenVerifier;
import com.bookspot.users.presentation.UserTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTokenServiceTest {
    @Mock private GoogleTokenVerifier googleTokenVerifier;
    @Mock private JwtProvider jwtProvider;
    @Mock private UserService userService;

    @Mock private GoogleIdToken.Payload mockGooglePayload;

    @InjectMocks private UserTokenService userTokenService;

    @Test
    void 생성된_토큰과_user_부가정보를_얻을_수_있음() {
        String idToken = "dummy-id-token";
        UserDto sampleUser = new UserDto(1L, "test@example.com", "nickname", "USER");
        Date expiredAt = new Date();
        when(googleTokenVerifier.verifyToken(idToken)).thenReturn(mockGooglePayload);
        when(userService.createOrFindUser(any(), any(), any())).thenReturn(sampleUser);
        when(jwtProvider.createToken(any())).thenReturn(new GeneratedToken("jwt-token", expiredAt));


        UserTokenResponse result = userTokenService.createToken(idToken);

        assertEquals("nickname",result.getNickname());
        assertEquals("jwt-token", result.getAccessToken());
        assertEquals("USER",result.getRole());
        assertEquals(expiredAt.getTime(), result.getExpiredAt());
    }

}