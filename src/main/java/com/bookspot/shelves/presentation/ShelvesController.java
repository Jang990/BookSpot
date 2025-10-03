package com.bookspot.shelves.presentation;

import com.bookspot.shelves.application.ShelvesQueryService;
import com.bookspot.shelves.presentation.dto.ShelfBookStatusResponse;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelvesBookStatusResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShelvesController {
    private final ShelvesQueryService shelvesQueryService;

    private Long parseLoginUserId(String userIdStr) {
        return userIdStr.equals("anonymousUser") ? null : Long.parseLong(userIdStr);
    }

    /**
     * @see com.bookspot.users.domain.exception.UserNotFoundException
     */
    @GetMapping("/api/users/{userId}/shelves")
    public ResponseEntity<ShelvesSummaryResponse> findUserShelves(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable(name = "userId") long shelvesOwnerUserId
    ) {
        Long loginUserId = parseLoginUserId(userIdStr);
        return ResponseEntity.ok(
                shelvesQueryService.findUserShelves(
                        loginUserId,
                        shelvesOwnerUserId
                )
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/api/users/shelves/books/{bookId}")
    public ResponseEntity<ShelvesBookStatusResponse> findUserShelvesBookStatus(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable(name = "bookId") long bookId
    ) {
        Long loginUserId = parseLoginUserId(userIdStr);
        return ResponseEntity.ok(
                new ShelvesBookStatusResponse(
                        List.of(
                                new ShelfBookStatusResponse(1, "추천하는 책", true, false),
                                new ShelfBookStatusResponse(2, "책장 A", false, true),
                                new ShelfBookStatusResponse(3, "개발 필독서", true, false),
                                new ShelfBookStatusResponse(4, "나중에 볼 책", false, true),
                                new ShelfBookStatusResponse(11, "영향을 받은 책", true, false)
                        )
                )
        );
    }

    /**
     * @see com.bookspot.shelves.domain.exception.ShelfNotFoundException
     * @see com.bookspot.shelves.domain.exception.ShelfPrivateAccessException
     */
    @GetMapping("/api/shelves/{shelfId}")
    public ResponseEntity<ShelfDetailResponse> findShelvesDetail(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable(name = "shelfId") long shelfId
    ) {
        return ResponseEntity.ok(
                shelvesQueryService.findShelfDetail(
                        parseLoginUserId(userIdStr),
                        shelfId
                )
        );
    }


}
