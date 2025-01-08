package com.bookspot.stock.presentation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Data
public class StockSearchForm {
    private String keywordInput;

    @NotNull
    @Range(min = -90, max = 90)
    private Double latitude;

    @NotNull
    @Range(min = -180, max = 180)
    private Double longitude;

    @Size(max = 10)
    private List<Long> bookId;

}
