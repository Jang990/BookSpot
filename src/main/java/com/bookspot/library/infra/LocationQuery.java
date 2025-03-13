package com.bookspot.library.infra;

import com.bookspot.library.presentation.LibraryDistanceResponse;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class LocationQuery {
    // 데이터 매핑
    protected static final RowMapper<LibraryDistanceResponse> ROW_MAPPER =
            (rs, rowNum) -> new LibraryDistanceResponse(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("longitude"),
                    rs.getDouble("latitude"),
                    rs.getString("address"),
                    rs.getString("home_page"),
                    rs.getString("closed_info"),
                    rs.getString("operating_info")
            );

    private final String LOCATION_FILED_NAME = "location";

    public String createLibrarySearchQuery(LocationMBR locationMBR) {
        return """
                SELECT id, name,
                    ST_X(location) AS longitude,
                    ST_Y(location) AS latitude,
                    address,
                    home_page,
                    closed_info,
                    operating_info
                FROM library
                WHERE %s
                """.formatted(
                        locationMBR.mbrContains(LOCATION_FILED_NAME)
        );
    }
}
