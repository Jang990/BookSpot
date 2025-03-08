package com.bookspot.library.application;

import com.bookspot.library.infra.LibraryRepositoryForView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepositoryForView repositoryForView;

    /*public List<LibraryDistanceResponse> findLibrariesWithin5km(double latitude, double longitude) {
        // TODO: Page<T> 반환으로 변경할 것
        return repositoryForView.findLibrariesWithinRadius(
                latitude, longitude,
                PageRequest.of(0, 10))
                .getContent();
    }*/
}
