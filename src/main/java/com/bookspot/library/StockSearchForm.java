package com.bookspot.library;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class StockSearchForm {
    private double latitude;
    private double longitude;
    private List<Long> bookId;
}
