package com.bookspot.book.infra.search;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Builder
public class BookSearchRequest {
    private List<Long> bookIds;
    private String keyword;
    private Long libraryId;
    private Pageable pageable;

    public boolean hasBookIds() {
        return bookIds != null && !bookIds.isEmpty();
    }

    public boolean hasKeyword() {
        return StringUtils.hasText(keyword);
    }

    public boolean hasLibraryId() {
        return libraryId != null;
    }
}
