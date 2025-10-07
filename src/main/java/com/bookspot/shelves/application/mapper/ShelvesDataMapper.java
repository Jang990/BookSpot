package com.bookspot.shelves.application.mapper;

import com.bookspot.book.domain.Book;
import com.bookspot.book.presentation.response.BookPreviewResponse;
import com.bookspot.book.presentation.response.CategoryResponse;
import com.bookspot.shelfbooks.domain.ShelfBook;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ShelvesDataMapper {

    public ShelfDetailResponse transform(Shelves shelf, List<ShelfBook> shelfBooks) {
        List<Long> relatedBookIds = shelfBooks.stream()
                .map(ShelfBook::getBook)
                .map(Book::getId)
                .toList();

        return new ShelfDetailResponse(
                shelf.getId(), shelf.getName(),
                shelf.getCreatedAt().toString(), shelf.isPublic(),
                shelf.getBookCount(), relatedBookIds
        );
    }

    public ShelvesSummaryResponse transform(List<Shelves> shelves) {
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

    // TODO: 임시 코드. 이후에 책장 속 책을 받아오는 로직으로 바꿔야함
    private static List<BookPreviewResponse> randBooks() {
        List<BookPreviewResponse> allBooks = new ArrayList<>(mockBooks.values());
        Collections.shuffle(allBooks, ThreadLocalRandom.current());
        int size = ThreadLocalRandom.current().nextInt(1, 6);
        return allBooks.subList(0, Math.min(size, allBooks.size()));
    }

    private static final Map<Long, BookPreviewResponse> mockBooks = Map.ofEntries(
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
