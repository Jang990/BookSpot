package com.bookspot.library.application;

import com.bookspot.library.application.query.Location;
import com.bookspot.library.domain.Library;
import com.bookspot.library.domain.LibraryRepository;
import com.bookspot.library.infra.LibraryRepositoryForView;
import com.bookspot.library.infra.LocationMBR;
import com.bookspot.library.presentation.LibraryDistanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepositoryForView repositoryForView;
    private final LibraryRepository libraryRepository;

    public List<LibraryDistanceResponse> findLibraries(Location nw, Location se) {
        return repositoryForView.findLibrariesInBound(
                new LocationMBR(
                        nw.latitude(), nw.longitude(),
                        se.latitude(), se.longitude()
                )
        );
    }

    public LibraryDistanceResponse findLibrary(long libraryId) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(IllegalArgumentException::new);

        return toResponse(library);
    }

    private LibraryDistanceResponse toResponse(Library library) {
        return new LibraryDistanceResponse(
                library.getId(),
                library.getName(),
                library.getLocation().getY(),
                library.getLocation().getX(),
                library.getAddress(),
                library.getHomePage(),
                library.getClosedInfo(),
                library.getOperatingInfo()
        );
    }

    /*public List<LibraryDistanceResponse> findLibrariesWithin5km(double latitude, double longitude) {
        // TODO: Page<T> 반환으로 변경할 것
        return repositoryForView.findLibrariesWithinRadius(
                latitude, longitude,
                PageRequest.of(0, 10))
                .getContent();
    }*/
}
