package com.bookspot.shelves.presentation;

import com.bookspot.shelves.application.ShelfBooksManageService;
import com.bookspot.shelves.presentation.dto.request.BulkShelfIdsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
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
    @PostMapping("/api/shelves/{shelfId}/books/{bookId}")
    public ResponseEntity<Void> addBookToShelf(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("shelfId") long shelfId,
            @PathVariable("bookId") long bookId
    ) {
        shelfBooksManageService.addBookToShelves(Long.parseLong(userIdStr), shelfId, bookId);
        return ResponseEntity.noContent().build();
    }

    /**
     * @see com.bookspot.shelves.domain.exception.ShelfNotFoundException
     * @see com.bookspot.shelves.domain.exception.ShelfBookFullException
     * @see com.bookspot.shelves.domain.exception.ShelfForbiddenException
     * @see com.bookspot.shelfbooks.domain.exception.ShelfBookAlreadyExistsException
     */
    @PostMapping("/api/users/books/{bookId}/shelves")
    public ResponseEntity<Void> addBookToShelves(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("bookId") long bookId,
            @RequestBody BulkShelfIdsRequest request
    ) {
        shelfBooksManageService.addBookToShelves(
                Long.parseLong(userIdStr),
                request.getShelfIds(),
                bookId
        );
        return ResponseEntity.noContent().build();
    }

    /**
     * @see com.bookspot.shelves.domain.exception.ShelfNotFoundException
     * @see com.bookspot.shelves.domain.exception.ShelfForbiddenException
     * @see com.bookspot.shelves.domain.exception.ShelfAlreadyEmptyException
     * @see com.bookspot.shelfbooks.domain.exception.ShelfBookNotFoundException
     */
    @DeleteMapping("/api/users/books/{bookId}/shelves")
    public ResponseEntity<Void> removeBookToShelves(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable("bookId") long bookId,
            BulkShelfIdsRequest request
    ) {
        shelfBooksManageService.removeBookToShelves(
                Long.parseLong(userIdStr),
                request.getShelfIds(),
                bookId
        );
        return ResponseEntity.noContent().build();
    }

}
