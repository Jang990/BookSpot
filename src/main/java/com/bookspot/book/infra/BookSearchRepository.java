package com.bookspot.book.infra;

import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.book.infra.search.cond.SearchAfterCond;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import com.bookspot.book.infra.search.result.BookPageResult;
import com.bookspot.book.infra.search.result.BookSearchAfterResult;
import org.opensearch.client.opensearch._types.SortOptions;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookSearchRepository {
    BookPageResult search(BookSearchCond searchRequest, Pageable pageable, OpenSearchPageable pageable_TEMP);
    BookSearchAfterResult search(BookSearchCond searchCond, SearchAfterCond searchAfterCond, int pageSize);
}
