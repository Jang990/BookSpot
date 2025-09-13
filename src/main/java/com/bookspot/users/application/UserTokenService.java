package com.bookspot.users.application;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.dto.GeneratedToken;
import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.SocialTokenDetail;
import com.bookspot.users.infra.token.google.GoogleTokenVerifier;
import com.bookspot.users.infra.token.naver.NaverTokenVerifier;
import com.bookspot.users.presentation.UserTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenService {
    private final GoogleTokenVerifier googleTokenVerifier;
    private final NaverTokenVerifier naverTokenVerifier;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public UserTokenResponse createToken(String idToken, OAuthProvider provider) {
        SocialTokenDetail result = googleTokenVerifier.verifyToken(idToken);
        UserDto user = userService.createOrFindUser(result.email(), provider, result.subjectId());

        GeneratedToken token = jwtProvider.createToken(user);

        return new UserTokenResponse(user.nickname(), user.role(), token);
    }

    public UserTokenResponse createNaverToken(String idToken) {
        NaverTokenVerifier.NaverTokenDetail result = naverTokenVerifier.verifyToken(idToken);
        UserDto user = userService.createOrFindUser(result.email(), OAuthProvider.NAVER, result.subjectId());

        GeneratedToken token = jwtProvider.createToken(user);

        return new UserTokenResponse(user.nickname(), user.role(), token);
    }
}
