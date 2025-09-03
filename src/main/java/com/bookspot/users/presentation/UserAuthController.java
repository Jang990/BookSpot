package com.bookspot.users.presentation;

import com.bookspot.global.log.BasicLog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {
    @BasicLog
    @PostMapping("/google")
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody GoogleLoginRequest requestDto) {
        String hardcodedJwt = "jwt토큰";

        // 응답 객체 생성
        LoginResponse response = new LoginResponse(hardcodedJwt, "ROLE_USER");

        // 성공 응답 반환
        return ResponseEntity.ok(response);
    }


    public record GoogleLoginRequest(String idToken) { }

    public record LoginResponse(String accessToken, String role) {}

}
