package com.bookspot.book.presentation.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class BookIdsSearchRequest {
    @Size(min = 1, max = 50)
    private final List<Long> bookIds;
}
