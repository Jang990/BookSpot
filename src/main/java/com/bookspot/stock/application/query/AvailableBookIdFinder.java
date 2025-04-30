package com.bookspot.stock.application.query;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableBookIdFinder {
    private static  final String AVAILABLE_BOOK_SQL = """
            Select ls.book_id
            From library_stock ls
            Where ls.library_id = :libraryId And ls.book_id in (:bookIds)
            """;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Long> find(long libraryId, List<Long> bookIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("libraryId", libraryId)
                .addValue("bookIds", bookIds);

        return namedParameterJdbcTemplate
                .queryForList(AVAILABLE_BOOK_SQL, parameters, Long.class);
    }
}
