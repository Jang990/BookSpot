package com.bookspot.library;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LibraryDistanceDto {
    private String libraryName;
    private double distance;
}
