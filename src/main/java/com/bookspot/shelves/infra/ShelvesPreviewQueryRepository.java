package com.bookspot.shelves.infra;

import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ShelvesPreviewQueryRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ShelvesSummaryResponse findAllShelves(Pageable pageable, int thumbnailBookCount) {
        return executeQuery(pageable, null, thumbnailBookCount, true);
    }

    public ShelvesSummaryResponse findAllShelves(Pageable pageable, long ownerId, int thumbnailBookCount) {
        return executeQuery(pageable, ownerId, thumbnailBookCount, false);
    }

    public ShelvesSummaryResponse findPublicShelves(Pageable pageable, long ownerId, int thumbnailBookCount) {
        return executeQuery(pageable, ownerId, thumbnailBookCount, true);
    }

    private ShelvesSummaryResponse executeQuery(Pageable pageable, Long ownerId, int thumbnailBookCount, boolean publicOnly) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("pageSize", pageable.getPageSize())
                .addValue("offset", pageable.getOffset())
                .addValue("thumbnailBookCount", thumbnailBookCount);

        StringBuilder whereClause = new StringBuilder("WHERE 1=1");
        if (ownerId != null) {
            whereClause.append(" AND bs.user_id = :ownerId");
            params.addValue("ownerId", ownerId);
        }
        if (publicOnly) {
            whereClause.append(" AND bs.is_public = true");
        }

        String sql = String.format("""
                WITH
                RankedBooks AS (
                    SELECT
                        s.id AS shelf_id,
                        s.name,
                        s.book_count,
                        s.created_at AS shelf_created_at,
                        s.updated_at AS shelf_updated_at,
                        s.is_public,
                        s.user_id AS owner_id,
                        b.isbn13,
                        ROW_NUMBER() OVER(PARTITION BY s.id ORDER BY sb.created_at DESC) as rn
                    FROM (
                        SELECT id, name, book_count, created_at, updated_at, is_public, user_id
                        FROM shelves bs
                        %s
                        ORDER BY bs.updated_at DESC
                        LIMIT :pageSize OFFSET :offset
                    ) s
                    LEFT JOIN shelf_books sb ON s.id = sb.shelf_id
                    LEFT JOIN book b ON sb.book_id = b.id
                )
                SELECT * FROM RankedBooks
                WHERE rn <= :thumbnailBookCount
                ORDER BY shelf_updated_at DESC, rn ASC
                """, whereClause.toString(), pageable.getPageSize(), pageable.getOffset());

        List<ShelfSummaryResponse> summaryList = jdbcTemplate.query(sql, params, rs -> {
            Map<Long, ShelfSummaryResponse> map = new LinkedHashMap<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while (rs.next()) {
                long shelfId = rs.getLong("shelf_id");

                ShelfSummaryResponse dto = map.computeIfAbsent(shelfId, id -> {
                    try {
                        LocalDateTime createdAt = rs.getObject("shelf_created_at", LocalDateTime.class);
                        return new ShelfSummaryResponse(
                                id,
                                rs.getString("name"),
                                rs.getInt("book_count"),
                                createdAt != null ? createdAt.format(formatter) : null,
                                rs.getBoolean("is_public"),
                                new ArrayList<>(),
                                rs.getLong("owner_id")
                        );
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                String isbn13 = rs.getString("isbn13");
                if (isbn13 != null) {
                    dto.getThumbnailImageIsbn().add(isbn13);
                }
            }
            return new ArrayList<>(map.values());
        });

        return new ShelvesSummaryResponse(summaryList);
    }
}
