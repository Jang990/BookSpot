package com.bookspot.shelves.presentation;

import com.bookspot.shelves.application.ShelvesQueryService;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PublicShelvesController {
    private final ShelvesQueryService shelvesQueryService;

    @GetMapping("/api/shelves")
    public ResponseEntity<ShelvesSummaryResponse> findUserShelves(
            @PageableDefault(size = 12) Pageable pageable
    ) {
        return ResponseEntity.ok(
                shelvesQueryService.findPublicShelves(pageable)
        );
    }
}
