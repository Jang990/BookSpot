package com.bookspot.shelves.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users/shelves/{shelfId}")
@RestController
@RequiredArgsConstructor
public class ShelvesManageController {
    @PostMapping
    public ResponseEntity<Void> addShelf(
            @PathVariable("shelfId") long shelfId
    ) {
        return ResponseEntity.ok().build();
    }
}
