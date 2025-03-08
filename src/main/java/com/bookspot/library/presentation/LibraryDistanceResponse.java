package com.bookspot.library.presentation;

import lombok.Data;

@Data
public class LibraryDistanceResponse {
    private long libraryId;
    private String libraryName;
    private double distanceMeter;

    public LibraryDistanceResponse(long libraryId, String libraryName, double distanceMeter) {
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.distanceMeter = (int) Math.round(distanceMeter);
    }
}
