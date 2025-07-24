package com.bookspot.book.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {
    private long id;
    private String title;
}
