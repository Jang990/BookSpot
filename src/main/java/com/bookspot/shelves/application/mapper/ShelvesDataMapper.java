package com.bookspot.shelves.application.mapper;

import com.bookspot.book.domain.Book;
import com.bookspot.book.presentation.response.BookPreviewResponse;
import com.bookspot.book.presentation.response.CategoryResponse;
import com.bookspot.shelfbooks.domain.ShelfBook;
import com.bookspot.shelves.application.ShelvesQueryService;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import com.bookspot.users.domain.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ShelvesDataMapper {

    public ShelfDetailResponse transform(Shelves shelf, List<ShelfBook> shelfBooks, Users users) {
        List<Long> relatedBookIds = shelfBooks.stream()
                .map(ShelfBook::getBook)
                .map(Book::getId)
                .toList();

        return new ShelfDetailResponse(
                shelf.getId(), shelf.getName(),
                shelf.getCreatedAt().toString(), shelf.isPublic(),
                shelf.getBookCount(), relatedBookIds,
                users.getId()
        );
    }

    public ShelvesSummaryResponse transform(List<Shelves> shelves, Map<Long, String> bookIdsAndIsbn13) {
        // TODO: 참 애매하다. 책장마다 Users를 받아야 맞는거 같은데... 일단 구현 편의성을 위해 shelf에서 user.id를 뽑는다
        return new ShelvesSummaryResponse(
                shelves.stream()
                        .map(shelf ->
                                new ShelfSummaryResponse(
                                        shelf.getId(),
                                        shelf.getName(),
                                        shelf.getBookCount(),
                                        shelf.getCreatedAt().toString(),
                                        shelf.isPublic(),
                                        toIsbn13(shelf, bookIdsAndIsbn13),

                                        shelf.getUsers().getId()
                                )
                        ).toList()
        );
    }

    private List<String> toIsbn13(Shelves shelf, Map<Long, String> bookIdsAndIsbn13) {
        return shelf.getShelfBooks()
                .stream()
                .limit(ShelvesQueryService.THUMBNAIL_BOOK_COUNT)
                .map(ShelfBook::getBook)
                .map(Book::getId)
                .map(bookIdsAndIsbn13::get)
                .toList();
    }
}
