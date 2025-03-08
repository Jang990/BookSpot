package com.bookspot.library.infra;

import com.bookspot.library.presentation.LibraryDistanceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class LocationQuery {
    // 데이터 매핑
    protected static final RowMapper<LibraryDistanceResponse> ROW_MAPPER =
            (rs, rowNum) -> new LibraryDistanceResponse(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("distance"));

    private final String LOCATION_FILED_NAME = "location";

    public String createLibrarySearchQuery(LocationMBR locationMBR) {
        LocationPoint center = new LocationPoint(37.526527106062204, 126.75283453090972);
        return """
                SELECT id, name,
                    (ST_Distance_Sphere(location, %s)) AS distance
                FROM library
                WHERE %s
                ORDER BY distance ASC
                """.formatted(
                        center.toString(),
                        locationMBR.mbrContains(LOCATION_FILED_NAME)
        );
    }
}
