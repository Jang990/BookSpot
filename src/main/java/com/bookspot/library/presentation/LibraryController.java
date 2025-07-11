package com.bookspot.library.presentation;

import com.bookspot.global.log.BasicLog;
import com.bookspot.library.application.LibraryService;
import com.bookspot.library.application.query.Location;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@BasicLog
@RequestMapping("/api/libraries")
@RestController
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping
    public ResponseEntity<List<LibraryDistanceResponse>> findLibraries(
            @Valid LibrarySearchRequest request) {
        return ResponseEntity.ok(
                libraryService.findLibraries(
                        new Location(request.getNwLat(), request.getNwLon()),
                        new Location(request.getSeLat(), request.getSeLon())
                )
        );
    }

    @GetMapping("/{libraryId}")
    public ResponseEntity<LibraryDistanceResponse> findLibraries(
            @PathVariable long libraryId
    ) {
        return ResponseEntity.ok(
                libraryService.findLibrary(libraryId)
        );
    }
}
