package com.bookspot.shelves.application;

import com.bookspot.SpringBootWithH2Test;
import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import com.bookspot.test.TestInsertUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootWithH2Test
class ShelvesQueryServiceTest_findShelvesPreview {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ShelvesQueryService queryService;

    LocalDateTime base = LocalDateTime.now();

    // 최신순 정렬
    LocalDateTime ORDER_0 = base.plusMinutes(3);
    LocalDateTime ORDER_1 = base.plusMinutes(2);
    LocalDateTime ORDER_2 = base.plusMinutes(1);
    LocalDateTime ORDER_3 = base;

    @BeforeEach
    public void setUp() {
        /*
        사용자1
        책장1 - 책1, 책2, 책3 (public)
        책장2 - 책1, 책2 (private)
        책장3 - 책1, 책3 (public)

        사용자 2
        책장1 - 책1 (public)
        */

        // 책 생성 (id: 1, 2, 3)
        TestInsertUtils.bookBuilder().id(1L).isbn13(isbn(1)).insert(jdbcTemplate);
        TestInsertUtils.bookBuilder().id(2L).isbn13(isbn(2)).insert(jdbcTemplate);
        TestInsertUtils.bookBuilder().id(3L).isbn13(isbn(3)).insert(jdbcTemplate);

        // 사용자 생성 (id: 1, 2)
        TestInsertUtils.usersBuilder().id(1L).insert(jdbcTemplate);
        TestInsertUtils.usersBuilder().id(2L).insert(jdbcTemplate);

        // 책장 생성

        // 책장1 (public, [책1, 책2, 책3])
        TestInsertUtils.shelvesBuilder().id(1L).userId(1L).isPublic(true).name("책장1").updatedAt(ORDER_1).insert(jdbcTemplate);
        TestInsertUtils.shelfBooksBuilder().shelfId(1L).bookId(1L).createdAt(ORDER_1).insert(jdbcTemplate);
        TestInsertUtils.shelfBooksBuilder().shelfId(1L).bookId(2L).createdAt(ORDER_2).insert(jdbcTemplate);
        TestInsertUtils.shelfBooksBuilder().shelfId(1L).bookId(3L).createdAt(ORDER_3).insert(jdbcTemplate);

        // 책장2 (private, [책1, 책2])
        TestInsertUtils.shelvesBuilder().id(2L).userId(1L).isPublic(false).name("책장2").updatedAt(ORDER_2).insert(jdbcTemplate);
        TestInsertUtils.shelfBooksBuilder().shelfId(2L).bookId(1L).createdAt(ORDER_1).insert(jdbcTemplate);
        TestInsertUtils.shelfBooksBuilder().shelfId(2L).bookId(2L).createdAt(ORDER_2).insert(jdbcTemplate);

        // 책장3 (public, [책1, 책3])
        TestInsertUtils.shelvesBuilder().id(3L).userId(1L).isPublic(true).name("책장3").updatedAt(ORDER_3).insert(jdbcTemplate);
        TestInsertUtils.shelfBooksBuilder().shelfId(3L).bookId(1L).createdAt(ORDER_1).insert(jdbcTemplate);
        TestInsertUtils.shelfBooksBuilder().shelfId(3L).bookId(3L).createdAt(ORDER_2).insert(jdbcTemplate);

        // 사용자2-책장 (public, [책1])
        TestInsertUtils.shelvesBuilder().id(4L).userId(2L).isPublic(true).name("사용자2-책장").updatedAt(ORDER_0).insert(jdbcTemplate);
        TestInsertUtils.shelfBooksBuilder().shelfId(4L).bookId(1L).createdAt(ORDER_1).insert(jdbcTemplate);
    }

    @Test
    @DisplayName("타사용자의 책장 목록 조회 - public만 조회")
    public void test() {
        // when
        ShelvesSummaryResponse result = queryService.findUserShelves(2L, 1L);

        // then
        assertThat(result.bookshelvesSummary().size()).isEqualTo(2);

        // 책장 - updatedAt 최신순 정렬
        List<Long> expectedIds = result.bookshelvesSummary().stream().map(ShelfSummaryResponse::getId).toList();
        assertThat(expectedIds).isEqualTo(List.of(1L, 3L));

        // 내부 책 - createdAt 최신순 정렬
        ShelfSummaryResponse expectedShelfOne = result.bookshelvesSummary().get(0);
        assertThat(expectedShelfOne.getThumbnailImageIsbn())
                .isEqualTo(List.of(isbn(1), isbn(2), isbn(3)));
        ShelfSummaryResponse expectedShelfThree = result.bookshelvesSummary().get(1);
        assertThat(expectedShelfThree.getThumbnailImageIsbn())
                .isEqualTo(List.of(isbn(1), isbn(3)));
    }

    @Test
    @DisplayName("자신의 책장 목록 조회 - private도 조회")
    public void test2() {
        // when
        ShelvesSummaryResponse result = queryService.findUserShelves(1L, 1L);

        // then
        assertThat(result.bookshelvesSummary().size()).isEqualTo(3);

        // 책장 - updatedAt 최신순 정렬
        List<Long> expectedIds = result.bookshelvesSummary().stream().map(ShelfSummaryResponse::getId).toList();
        assertThat(expectedIds).isEqualTo(List.of(1L, 2L, 3L));

        // 내부 책 - createdAt 최신순 정렬
        assertThat(result.bookshelvesSummary().get(0).getThumbnailImageIsbn())
                .isEqualTo(List.of(isbn(1), isbn(2), isbn(3)));
        assertThat(result.bookshelvesSummary().get(1).getThumbnailImageIsbn())
                .isEqualTo(List.of(isbn(1), isbn(2)));
        assertThat(result.bookshelvesSummary().get(2).getThumbnailImageIsbn())
                .isEqualTo(List.of(isbn(1), isbn(3)));
    }

    @Test
    @DisplayName("전체 책장 목록 조회 - 사용자 상관없이 public 책장만 조회")
    public void test3() {
        // when
        ShelvesSummaryResponse result = queryService.findPublicShelves(PageRequest.of(0, 3));

        // 사용자2 - 책장(4) + 사용자1 - 책장 1,3
        assertThat(result.bookshelvesSummary().size()).isEqualTo(3);

        // 책장 - updatedAt 최신순 정렬
        List<Long> expectedIds = result.bookshelvesSummary().stream().map(ShelfSummaryResponse::getId).toList();
        assertThat(expectedIds).isEqualTo(List.of(4L, 1L, 3L));
    }

    public String isbn(long bookId) {
        return String.valueOf(bookId).repeat(13);
    }

}