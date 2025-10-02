package com.bookspot.shelves.presentation;

import com.bookspot.shelves.application.ShelfBooksManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("isAuthenticated()")
@RequestMapping("/api/shelves/{shelfId}/books")
@RestController
@RequiredArgsConstructor
public class ShelfBooksManageController {
    private final ShelfBooksManageService shelfBooksManageService;

    /**
     * @see com.bookspot.book.domain.exception.BookNotFoundException
     * @see com.bookspot.shelves.domain.exception.ShelfNotFoundException
     * @see com.bookspot.shelves.domain.exception.ShelfForbiddenException
     * @see com.bookspot.shelves.domain.exception.ShelfBookFullException
     * @see com.bookspot.shelfbooks.domain.exception.ShelfBookAlreadyExistsException
     */
    @PostMapping("/{bookId}")
    public ResponseEntity<Void> addBookToShelf(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("shelfId") long shelfId,
            @PathVariable("bookId") long bookId
    ) {
        shelfBooksManageService.addBookToShelf(Long.parseLong(userIdStr), shelfId, bookId);
        return ResponseEntity.noContent().build();
    }

}
