package com.bookspot.shelves.application.mapper;

import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;

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
}
