package com.bookspot.library.presentation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class LibrarySearchRequest {
    @NotNull
    @Range(min = -90, max = 90)
    private Double nwLat;

    @NotNull
    @Range(min = -180, max = 180)
    private Double nwLon;

    @NotNull
    @Range(min = -90, max = 90)
    private Double seLat;

    @NotNull
    @Range(min = -180, max = 180)
    private Double seLon;
}
