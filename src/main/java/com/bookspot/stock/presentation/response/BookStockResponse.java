package com.bookspot.stock.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookStockResponse {
    private long id;
    private String title;
    boolean available;
}
