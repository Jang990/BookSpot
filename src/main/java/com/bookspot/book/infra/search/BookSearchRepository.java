package com.bookspot.book.infra.search;

import org.springframework.data.domain.Pageable;

public interface BookSearchRepository {
    BookPageResult search(BookSearchCond searchRequest, Pageable pageable);
    BookSearchAfterResult search(BookSearchCond searchCond, SearchAfterCond searchAfterCond, int pageSize);
}
