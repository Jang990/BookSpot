package com.bookspot.shelves.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShelfSummaryResponse {
    private long id;
    private String name;
    private int bookCount;
    private String createdAt;
    @JsonProperty("isPublic")
    private boolean isPublic;
    private List<String> thumbnailImageIsbn;

    private long ownerId;
}
