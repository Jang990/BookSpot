package com.bookspot.book.infra;

import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import com.bookspot.book.infra.search.result.BookPageResult;
import com.bookspot.book.infra.search.result.BookSearchAfterResult;


public interface BookSearchRepository {
    BookPageResult search(BookSearchCond searchRequest, OpenSearchPageable pageable);
    BookSearchAfterResult search(BookSearchCond searchCond, SearchAfterCond searchAfterCond);
    BookDocument search(String isbn13);
}
