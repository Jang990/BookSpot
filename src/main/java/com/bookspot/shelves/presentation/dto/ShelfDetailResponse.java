package com.bookspot.shelves.presentation.dto;

import com.bookspot.book.presentation.response.BookPreviewResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShelfDetailResponse {
    private long id;
    private String name;
    private String createdAt;
    @JsonProperty("isPublic")
    private boolean isPublic;
    private int bookCount;
    List<BookPreviewResponse> books;
}
