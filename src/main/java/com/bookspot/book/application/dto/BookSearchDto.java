package com.bookspot.book.application.dto;

import com.bookspot.book.presentation.request.CategoryLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchDto {
    private String title;
    private List<Long> bookIds;
    private Long libraryId;
    private Integer categoryId;
    private CategoryLevel categoryLevel;
}
