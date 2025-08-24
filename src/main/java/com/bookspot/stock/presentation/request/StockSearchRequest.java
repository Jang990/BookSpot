package com.bookspot.stock.presentation.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class StockSearchRequest {
    @NotEmpty
    private List<Long> libraryIds;

    @Size(max = 20)
    private List<Long> bookIds;
}
