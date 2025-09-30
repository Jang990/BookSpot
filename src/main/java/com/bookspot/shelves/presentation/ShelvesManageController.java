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

    /**
     * @see com.bookspot.shelves.domain.exception.ShelfNotFoundException
     * @see com.bookspot.shelves.domain.exception.ShelfForbiddenException
     */
    @DeleteMapping("/{shelfId}")
    public ResponseEntity<Void> deleteShelf(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("shelfId") long shelfId
    ) {
        shelvesManageService.delete(
                Long.parseLong(userIdStr),
                shelfId
        );

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{shelfId}")
    public ResponseEntity<ShelfDetailResponse> updateShelf(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("shelfId") long shelfId,
            @Valid @RequestBody ShelfCreationRequest request
    ) {
        return ResponseEntity.ok(
                shelvesManageService.update(
                        Long.parseLong(userIdStr),
                        shelfId, request
                )
        );
    }
}
