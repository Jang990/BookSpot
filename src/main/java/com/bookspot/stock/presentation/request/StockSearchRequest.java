package com.bookspot.stock.presentation.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class StockSearchRequest {
    private List<Long> libraryIds;

    @Size(max = 10)
    private List<Long> bookIds;
}
