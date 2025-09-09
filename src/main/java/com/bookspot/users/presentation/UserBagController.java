package com.bookspot.users.presentation;

import com.bookspot.bag.application.BagBookService;
import com.bookspot.users.application.UserBagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserBagController {
    private final UserBagService userBagService;
    private final BagBookService bagBookService;

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

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/api/users/bag/books/{bookId}")
    public ResponseEntity<Void> deleteBookFromBag(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("bookId") long bookId
    ) {
        long userId = Long.parseLong(userIdStr);
        userBagService.deleteBook(userId, bookId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/api/users/bag/books")
    public ResponseEntity<Void> clearBagBook(@AuthenticationPrincipal String userIdStr) {
        long userId = Long.parseLong(userIdStr);
        userBagService.clearBag(userId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/users/bag/books/ids")
    public ResponseEntity<BagBookIdsResponse> getUserBagBookIds(@AuthenticationPrincipal String userIdStr) {
        long userId = Long.parseLong(userIdStr);
        return ResponseEntity.ok(
                new BagBookIdsResponse(bagBookService.findBookIds(userId))
        );
    }
}
