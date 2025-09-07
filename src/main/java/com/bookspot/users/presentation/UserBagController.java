package com.bookspot.users.presentation;

import com.bookspot.users.application.UserBagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserBagController {
    private final UserBagService userBagService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/api/users/bag/books/{bookId}")
    public ResponseEntity<Void> addBookToBag(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("bookId") long bookId
    ) {
        long userId = Long.parseLong(userIdStr);
        userBagService.addBook(userId, bookId);
        return ResponseEntity.noContent().build();
    }

}
