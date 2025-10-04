package com.bookspot.shelves.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShelfBookStatusResponse {
    private final long id;
    private final String name;
    private final int bookCount;
    @JsonProperty("isPublic")
    private final boolean isPublic;
    @JsonProperty("isExists")
    private final boolean isExists;
}
