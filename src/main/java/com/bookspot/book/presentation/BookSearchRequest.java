package com.bookspot.book.presentation;

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

    public boolean hasBookIds() {
        return bookIds != null && !bookIds.isEmpty();
    }

    public boolean hasTitle() {
        return title != null && !title.isBlank();
    }

    public boolean hasLibraryId() {
        return libraryId != null;
    }

    public boolean isCriteriaMissing() {
        return !hasTitle() && !hasBookIds() && !hasLibraryId();
    }
}
