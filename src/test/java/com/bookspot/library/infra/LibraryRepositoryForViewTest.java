package com.bookspot.library.infra;

import com.bookspot.library.LibraryDistanceDto;
import com.bookspot.library.LibraryDistanceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryRepositoryForViewTest {

    @Autowired
    private LibraryRepositoryForView repository;

//    @Test
    @DisplayName("삼산고등학교 기준 반경 검색")
    void test() {
        Page<LibraryDistanceResponse> result = repository.findLibrariesWithinRadius(
                37.521449, 126.7456143,
                PageRequest.of(0, 10));
        for (LibraryDistanceResponse libraryDistance : result) {
            System.out.println(libraryDistance);
        }
    }
}