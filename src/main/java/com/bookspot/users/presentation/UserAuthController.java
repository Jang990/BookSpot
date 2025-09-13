package com.bookspot.users.presentation;

import com.bookspot.users.application.UserTokenService;
import com.bookspot.users.domain.OAuthProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserAuthController {
    private final UserTokenService userTokenService;

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

    @PostMapping("google")
    public ResponseEntity<UserTokenResponse> googleLogin(
            @Valid @RequestBody LoginRequest requestDto
    ) {
        return ResponseEntity.ok(
                userTokenService.createToken(
                        requestDto.idToken(),
                        OAuthProvider.GOOGLE
                )
        );
    }

    @PostMapping("naver")
    public ResponseEntity<UserTokenResponse> naverLogin(@Valid @RequestBody LoginRequest requestDto) {
        return ResponseEntity.ok(
                userTokenService.createToken(
                        requestDto.idToken(),
                        OAuthProvider.NAVER
                )
        );
    }
}
