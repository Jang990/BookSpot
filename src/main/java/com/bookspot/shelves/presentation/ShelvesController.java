package com.bookspot.shelves.presentation;

import com.bookspot.book.presentation.response.BookPreviewResponse;
import com.bookspot.book.presentation.response.CategoryResponse;
import com.bookspot.shelves.application.ShelvesQueryService;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ShelvesController {
    private final ShelvesQueryService shelvesQueryService;

    /**
     * @see com.bookspot.users.domain.exception.UserNotFoundException
     */
    @GetMapping("/api/users/{userId}/shelves")
    public ResponseEntity<ShelvesSummaryResponse> findUserShelves(
            @AuthenticationPrincipal String userIdStr,
            @PathVariable(name = "userId") long shelvesOwnerUserId
    ) {
        Long loginUserId = userIdStr.equals("anonymousUser") ? null : Long.parseLong(userIdStr);
        return ResponseEntity.ok(
                shelvesQueryService.findUserShelves(
                        loginUserId,
                        shelvesOwnerUserId
                )
        );
    }

    @GetMapping("/api/shelves/{shelfId}")
    public ResponseEntity<ShelfDetailResponse> findShelvesDetail(
            @PathVariable(name = "shelfId") long shelfId

    ) {
        if(shelfId == 1)
            return ResponseEntity.ok(
                    new ShelfDetailResponse(
                            1,
                            "개발 필독서",
                            "2024-01-01",
                            true,
                            4,
                            List.of(mockBooks.get(1L), mockBooks.get(2L), mockBooks.get(3L))
                    )
            );
        if(shelfId == 2)
            return ResponseEntity.ok(
                    new ShelfDetailResponse(
                            2L,
                            "고전 문학",
                            "2024-01-05",
                            false,
                            3,
                            List.of(mockBooks.get(4L), mockBooks.get(5L))
                    )
            );

        return ResponseEntity.ok(
                new ShelfDetailResponse(
                        3L,
                        "읽고 싶은 책들",
                        "2024-02-01",
                        false,
                        0,
                        List.of()
                )
        );
    }

    Map<Long, BookPreviewResponse> mockBooks = Map.ofEntries(
            Map.entry(1L, new BookPreviewResponse(
                    1,
                    "클린 코드",
                    "로버트 C. 마틴",
                    "9788966260959",
                    2013,
                    "인사이트",
                    1250,
                    0,
                    new CategoryResponse(1, "프로그래밍"),
                    "2024-01-15"
            )),
            Map.entry(2L, new BookPreviewResponse(
                    2,
                    "이펙티브 자바",
                    "조슈아 블로크",
                    "9788966262281",
                    2018,
                    "인사이트",
                    980,
                    0,
                    new CategoryResponse(1, "프로그래밍"),
                    "2024-01-20"
            )),
            Map.entry(3L, new BookPreviewResponse(
                    3,
                    "리팩터링",
                    "마틴 파울러",
                    "9791162242742",
                    2019,
                    "한빛미디어",
                    750,
                    0,
                    new CategoryResponse(1, "프로그래밍"),
                    "2024-02-01"
            )),
            Map.entry(4L, new BookPreviewResponse(
                    4,
                    "데미안",
                    "헤르만 헤세",
                    "9788937460449",
                    2009,
                    "민음사",
                    2100,
                    0,
                    new CategoryResponse(2, "소설"),
                    "2024-02-10"
            )),
            Map.entry(5L, new BookPreviewResponse(
                    5,
                    "1984",
                    "조지 오웰",
                    "9788937460777",
                    2003,
                    "민음사",
                    1800,
                    0,
                    new CategoryResponse(2, "소설"),
                    "2024-02-15"
            ))
    );
}
