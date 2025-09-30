package com.bookspot.shelves.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/users/shelves/{shelfId}")
@RestController
@RequiredArgsConstructor
public class ShelvesManageController {

    @PostMapping
    public ResponseEntity<Void> addShelf(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("shelfId") long shelfId
    ) {
        return ResponseEntity.ok().build();
    }
}
