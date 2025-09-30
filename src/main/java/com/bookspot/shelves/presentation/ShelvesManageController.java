package com.bookspot.shelves.presentation;

import com.bookspot.shelves.application.ShelvesManageService;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.request.ShelfCreationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/users/shelves")
@RestController
@RequiredArgsConstructor
public class ShelvesManageController {
    private final ShelvesManageService shelvesManageService;

    /**
     * @see com.bookspot.users.domain.exception.UserNotFoundException
     */
    @PostMapping
    public ResponseEntity<ShelfDetailResponse> createShelf(
            @AuthenticationPrincipal String userIdStr,
            @Valid @RequestBody ShelfCreationRequest request
    ) {
        return ResponseEntity.ok(
                shelvesManageService.create(
                        Long.parseLong(userIdStr),
                        request
                )
        );
    }
}
