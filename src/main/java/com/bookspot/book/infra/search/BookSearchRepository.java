package com.bookspot.book.infra.search;

import org.springframework.data.domain.Page;

public interface BookSearchRepository {
    Page<BookDocument> search(BookSearchCond searchRequest);
}
