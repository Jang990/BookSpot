package com.bookspot.shelves.presentation;

import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShelvesController {

    @GetMapping("/api/users/{userId}/shelves")
    public ResponseEntity<ShelvesSummaryResponse> findUserShelves(@PathVariable(name = "userId") long userId) {
        return ResponseEntity.ok(
                new ShelvesSummaryResponse(
                        List.of(
                                new ShelfSummaryResponse(
                                        1, "개발 필독서", 3,
                                        "2024-01-01", true,
                                        List.of("9788966260959", "9788966262281", "9791162242742")
                                ),
                                new ShelfSummaryResponse(
                                        2, "고전 문학", 3,
                                        "2024-01-05", false,
                                        List.of("9788937460449", "9788937460777")
                                ),
                                new ShelfSummaryResponse(
                                        3, "읽고 싶은 책들", 0,
                                        "2024-02-01", false,
                                        List.of()
                                )
                        )
                )
        );
    }
}
