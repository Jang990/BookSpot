package com.bookspot.book.infra.search;

import com.bookspot.book.infra.BookDocument;
import com.bookspot.book.infra.BookSearchRepository;
import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import com.bookspot.book.infra.search.result.BookPageResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Profile("prod")
class BookOpenSearchRepositoryTest_SearchQuality {
    private final PageRequest TOP3_PAGEABLE = PageRequest.of(0, 3);

    @Autowired
    BookSearchRepository repository;

    @Test
    void 이기적_검색어_TOP3() {
        BookSearchCond request = BookSearchCond.builder()
                .keyword("이기적")
                .build();

        BookPageResult result = repository.search(
                request,
                OpenSearchPageable.sortByLoanCount(TOP3_PAGEABLE)
        );

        List<BookDocument> bookDocuments = result.books().stream().toList();

        // 이기적 유전자:진화론의 새로운 패러다임
        // https://www.yes24.com/product/goods/4078717
        assertEquals("9788932471631", bookDocuments.get(0).getIsbn13());

        // 이기적 유전자 (40주년 기념판)
        // https://product.kyobobook.co.kr/detail/S000000576524
        assertEquals("9788932473901", bookDocuments.get(1).getIsbn13());

        // 이기적 유전자
        // https://product.kyobobook.co.kr/detail/S000000576311
        assertEquals("9788932471112", bookDocuments.get(2).getIsbn13());
    }
}