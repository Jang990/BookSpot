package com.bookspot.book.infra.search;

import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.infra.search.result.BookPageResult;
import com.bookspot.book.infra.search.result.BookSearchAfterResult;
import org.springframework.data.domain.Pageable;

public interface BookSearchRepository {
    BookPageResult search(BookSearchCond searchRequest, Pageable pageable);
    BookSearchAfterResult search(BookSearchCond searchCond, SearchAfterCond searchAfterCond, int pageSize);
}
