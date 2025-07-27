package com.bookspot.book.presentation.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BookSearchRequest {
    @Size(min = 2)
    private String title;
    private List<Long> bookIds;
    private Long libraryId;
}
