package com.bookspot.library.infra;

import com.bookspot.library.LibraryDistanceDto;
import com.bookspot.library.LibraryDistanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryRepositoryForView {
    private static final String SEARCH_SQL = """
            SELECT id, name,
                   (ST_Distance_Sphere(location, ST_GeomFromText(?, 4326))) AS distance
            FROM library
            WHERE ST_Distance_Sphere(location, ST_GeomFromText(?, 4326)) <= 5000
            ORDER BY distance ASC
            LIMIT ? OFFSET ?
            """;
    private static final String COUNT_SQL = """
                SELECT COUNT(*)
                FROM library
                WHERE ST_Distance_Sphere(location, ST_GeomFromText(?, 4326)) <= 5000
                """;
    private static final String POINT_FORMAT = "POINT(%f %f)";

    private final JdbcTemplate jdbcTemplate;

    public Page<LibraryDistanceResponse> findLibrariesWithinRadius(double latitude, double longitude, Pageable pageable) {
        final String userLocation = String.format(POINT_FORMAT, latitude, longitude);

        // 데이터 매핑
        RowMapper<LibraryDistanceResponse> rowMapper =
                (rs, rowNum) -> new LibraryDistanceResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("distance"));

        // 데이터 조회
        List<LibraryDistanceResponse> libraries = jdbcTemplate.query(
                SEARCH_SQL, rowMapper,
                userLocation, userLocation,
                pageable.getPageSize(), pageable.getOffset());

        // 총 데이터 개수 조회
        int totalElements = jdbcTemplate.queryForObject(COUNT_SQL, Integer.class, userLocation);

        // Page 객체 생성
        return new PageImpl<>(
                libraries,
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize()),
                totalElements);
    }

}
