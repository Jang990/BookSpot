package com.bookspot.book.infra.search.pagination;

import com.bookspot.book.infra.search.cond.SearchAfterCond;
import lombok.Getter;
import org.opensearch.client.opensearch._types.SortOptions;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;

@Getter
public class OpenSearchAfter {
    private final int pageSize;
    private final List<String> searchAfter;
    private final List<SortOptions> sortOptions;

    public OpenSearchAfter(SearchAfterCond searchAfterCond) {
        this.pageSize = searchAfterCond.pageSize();
        this.sortOptions = searchAfterCond.sortOptions();
        this.searchAfter = List.of(
                String.valueOf(searchAfterCond.lastLoanCount()),
                searchAfterCond.lastScore(),
                String.valueOf(searchAfterCond.lastBookId())
        );
    }
}
