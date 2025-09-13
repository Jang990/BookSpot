package com.bookspot.users.infra.token.kakao;

import com.bookspot.global.api.ApiRequester;
import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.auth.SocialTokenDetail;
import com.bookspot.users.domain.auth.SocialTokenVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoTokenVerifier implements SocialTokenVerifier {

    private static final String URL = "https://kapi.kakao.com/v2/user/me";
    private final ApiRequester apiRequester;

    @Override
    public boolean supports(OAuthProvider provider) {
        return provider == OAuthProvider.KAKAO;
    }
    
    @Override
    public SocialTokenDetail verifyToken(String token) {
        try {
            Map<String, String> headers = Map.of("Authorization", "Bearer " + token);

            KakaoUserInfo userInfo = apiRequester.get(URL, headers, KakaoUserInfo.class);
            return new SocialTokenDetail(
                    userInfo.id().toString(),
                    userInfo.kakao_account() == null ? null : userInfo.kakao_account().email()
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Kakao token", e);
        }
    }
}