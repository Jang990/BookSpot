package com.bookspot.users.infra.token.naver;

import com.bookspot.global.api.ApiRequester;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverTokenVerifier {
    private static final String NAVER_PROFILE_URL = "https://openapi.naver.com/v1/nid/me";
    private final ApiRequester apiRequester;

    public NaverTokenDetail verifyToken(String accessToken) {
        try {
            String url = NAVER_PROFILE_URL + "?access_token=" + accessToken;

            NaverApiResponse response = apiRequester.get(url, NaverApiResponse.class);

            if (!"00".equals(response.resultcode())) {
                log.error("네이버 API 호출 실패: {} - {}", response.resultcode(), response.message());
                throw new RuntimeException("네이버 사용자 정보 조회 실패: " + response.message());
            }

            NaverUserInfo userInfo = response.response();

            // 토큰 검증 성공 - 사용자 정보 출력
            return new NaverTokenDetail(userInfo.id(), userInfo.email());
        } catch (Exception e) {
            log.error("네이버 토큰 검증 중 오류 발생", e);
            throw new RuntimeException("네이버 토큰 검증 실패", e);
        }
    }

    public record NaverTokenDetail(String subjectId, String email) { }

}
