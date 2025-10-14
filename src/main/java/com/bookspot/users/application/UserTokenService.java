package com.bookspot.users.application;

import com.bookspot.global.auth.JwtProvider;
import com.bookspot.global.auth.dto.GeneratedToken;
import com.bookspot.users.application.helper.SocialTokenVerifierSelector;
import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.auth.SocialTokenDetail;
import com.bookspot.users.domain.auth.SocialTokenVerifier;
import com.bookspot.users.presentation.UserTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTokenService {
    private final SocialTokenVerifierSelector socialTokenVerifierSelector;
    private final JwtProvider jwtProvider;
    private final UserService userService;

    public UserTokenResponse createToken(String idToken, OAuthProvider provider) {
        SocialTokenVerifier tokenVerifier = socialTokenVerifierSelector.select(provider);
        SocialTokenDetail result = tokenVerifier.verifyToken(idToken);
        UserDto user = userService.createOrFindUser(result.email(), provider, result.subjectId());

        GeneratedToken token = jwtProvider.createToken(user);

        return new UserTokenResponse(user.id(), user.nickname(), user.role(), token);
    }
}
