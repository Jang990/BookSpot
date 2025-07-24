package com.bookspot.book.infra.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookSearchRepository {
    Page<BookDocument> search(BookSearchCond searchRequest, Pageable pageable);
}
