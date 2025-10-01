package com.bookspot.shelves.application.mapper;

import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;

import java.util.List;

public class ShelvesDataMapper {
    public static ShelfDetailResponse transform(Shelves shelf) {
        // TODO: 관련 도서 상세 정보 필요
        return new ShelfDetailResponse(
                shelf.getId(), shelf.getName(),
                shelf.getCreatedAt().toString(), shelf.isPublic(),
                shelf.getBookCount(), List.of()
        );
    }

    public static ShelvesSummaryResponse transform(List<Shelves> shelves) {
        return new ShelvesSummaryResponse(
                shelves.stream()
                        .map(shelf ->
                                new ShelfSummaryResponse(
                                        shelf.getId(),
                                        shelf.getName(),
                                        shelf.getBookCount(),
                                        shelf.getCreatedAt().toString(),
                                        shelf.isPublic(),
                                        List.of()
                                )
                        ).toList()
        );
    }
}
