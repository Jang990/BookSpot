package com.bookspot.library.infra;

import com.bookspot.library.presentation.LibraryDistanceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
class LibraryRepositoryForViewTest {

    @Autowired
    private LibraryRepositoryForView repository;

    @Test
    @DisplayName("부평구 범위 검색")
    void test() {
        List<LibraryDistanceResponse> result = repository.findLibrariesInBound(
                new LocationMBR(
                        37.52739176387812, 126.75269026468787,
                        37.50568658729097, 126.71657056097237
                ),
                PageRequest.of(0, 10)
        );
        for (LibraryDistanceResponse libraryDistance : result) {
            System.out.println(libraryDistance);
        }
    }
}