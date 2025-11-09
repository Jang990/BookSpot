package com.bookspot.users.presentation;

import com.bookspot.users.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * @see com.bookspot.users.domain.exception.UserNotFoundException
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserDetailResponse> findMyInfo(
            @AuthenticationPrincipal String userIdStr
    ) {
        long userId = Long.parseLong(userIdStr);
        return ResponseEntity.ok(userService.findMyInfo(userId));
    }
}
