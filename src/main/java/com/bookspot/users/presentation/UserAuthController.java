package com.bookspot.users.presentation;

import com.bookspot.users.infra.GoogleTokenVerifier;
import com.bookspot.global.auth.JwtProvider;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthController {
    private final GoogleTokenVerifier googleTokenVerifier;
    private final JwtProvider jwtProvider;

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        /*
        access 토큰 추출 + access 토큰을 유효기간 만큼 블랙리스트에 저장(redis + ttl)
        로그아웃된 토큰으로 요청이 오면 요청 거부

        TODO: 이후 redis를 붙일 일이 있다면 블랙 리스트 구현 필요.
            지금은 액세스 토큰 기간을 줄여서 탈취를 대비할 것
         */

        return ResponseEntity.ok("성공적으로 로그아웃되었습니다.");
    }

    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody GoogleLoginRequest requestDto) {
        GoogleIdToken.Payload result = googleTokenVerifier.verifyToken(requestDto.idToken());
//        System.out.println(result.getEmail());
//        System.out.println(result.getUnknownKeys()); // {name=, picture=..., given_name=, family_name=}

        String token = jwtProvider.createToken(result.getEmail(), "google");

        // 응답 객체 생성
        LoginResponse response = new LoginResponse(token, "ROLE_USER");

        // 성공 응답 반환
        return ResponseEntity.ok(response);
    }


    public record GoogleLoginRequest(String idToken) { }

    public record LoginResponse(String accessToken, String role) {}

}
