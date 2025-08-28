package com.bookspot.stock.presentation.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class StockLoanStateSearchRequest {
    @Size(min = 1, max = 20)
    private List<Long> stockIds;
}
