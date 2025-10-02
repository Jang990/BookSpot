package com.bookspot.shelves.application.mapper;

import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ShelvesDataMapper {

    public ShelfDetailResponse transform_TEMP(Shelves shelf) {
        // TODO: 관련 도서 상세 정보 필요
        return new ShelfDetailResponse(
                shelf.getId(), shelf.getName(),
                shelf.getCreatedAt().toString(), shelf.isPublic(),
                shelf.getBookCount(), List.of()
        );
    }

    public ShelvesSummaryResponse transform_TEMP(List<Shelves> shelves) {
        return new ShelvesSummaryResponse(
                shelves.stream()
                        .map(shelf ->
                                new ShelfSummaryResponse(
                                        shelf.getId(),
                                        shelf.getName(),
                                        shelf.getBookCount(),
                                        shelf.getCreatedAt().toString(),
                                        shelf.isPublic(),
                                        randThumbnails()
                                )
                        ).toList()
        );
    }

    private static final List<List<String>> list = List.of(
            List.of("9788966260959", "9788966262281", "9791162242742"),
            List.of("9788937460449", "9788937460777"),
            List.of()
    );

    // TODO: 임시 코드. 이후에 책장 속에 책을 추가하고 실제 isbn13을 가져오는 쿼리로 가져와서 합쳐야함.
    private static List<String> randThumbnails() {
        int randIdx = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(randIdx);
    }
}
