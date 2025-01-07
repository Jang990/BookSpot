package com.bookspot.book.presentation;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class BookSearchRequest {
    @Size(min = 2)
    private String title;
}
