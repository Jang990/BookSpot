package com.bookspot.stock.presentation.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class StockSearchRequest {
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

    @Size(max = 10)
    private List<Long> bookIds;
}
