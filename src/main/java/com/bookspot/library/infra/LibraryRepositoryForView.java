package com.bookspot.library.infra;

import com.bookspot.library.presentation.LibraryDistanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryRepositoryForView {
    private final JdbcTemplate jdbcTemplate;
    private final LocationQuery queryCreator;

    public List<LibraryDistanceResponse> findLibrariesInBound(LocationMBR location, Pageable pageable) {
        return jdbcTemplate.query(
                queryCreator.createLibrarySearchQuery(location, pageable),
                LocationQuery.ROW_MAPPER
        );
    }

}
