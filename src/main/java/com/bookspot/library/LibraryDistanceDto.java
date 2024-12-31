package com.bookspot.library;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibraryDistanceDto {
    private long libraryId;
    private String libraryName;
    private double distance;
}
