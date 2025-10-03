package com.bookspot.shelves.infra;

import com.bookspot.shelves.presentation.dto.ShelfBookStatusResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bookspot.shelfbooks.domain.QShelfBook.*;
import static com.bookspot.shelves.domain.QShelves.*;

@Repository
@RequiredArgsConstructor
public class ShelvesQuerydslRepository {
    private final JPAQueryFactory query;

    public List<ShelfBookStatusResponse> findShelvesStatus(long userId, long bookId) {
        return query.select(
                    Projections.constructor(
                            ShelfBookStatusResponse.class,
                            shelves.id,
                            shelves.name,
                            shelves.isPublic,
                            shelfBook.id.isNotNull()
                    )
                )
                .from(shelves)
                .leftJoin(shelfBook)
                    .on(
                            shelfBook.shelf.eq(shelves)
                                    .and(shelfBook.book.id.eq(bookId))
                    )
                .where(shelves.users.id.eq(userId))
                .orderBy(shelves.updatedAt.asc())
                .fetch();
    }
}
