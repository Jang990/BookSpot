package com.bookspot.library.presentation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibraryDistanceResponse {
    private long libraryId;
    private String libraryName;
    private double latitude;
    private double longitude;
    private double distanceMeter;
}
