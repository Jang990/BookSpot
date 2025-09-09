package com.bookspot.users.application;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.users.infra.GoogleTokenVerifier;
import com.bookspot.users.presentation.UserTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenService {
    private final GoogleTokenVerifier googleTokenVerifier;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    private static final String PROVIDER_TYPE_GOOGLE = "google";

    public UserTokenResponse createToken(String idToken) {
        GoogleIdToken.Payload result = googleTokenVerifier.verifyToken(idToken);
        UserDto user = userService.createOrFindUser(result.getEmail(), PROVIDER_TYPE_GOOGLE, result.getSubject());

        String token = jwtProvider.createToken(user).accessToken();

        return new UserTokenResponse(user.nickname(), token, user.role());
    }
}
