package com.bookspot.book.infra.search;

import com.bookspot.book.infra.BookCommonFields;
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
    private final PageRequest TOP10_PAGEABLE = PageRequest.of(0, 10);

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

    @Test
    void 이기적_검색어_개선된케이스1() {
        BookSearchCond request = BookSearchCond.builder()
                .keyword("이기적")
                .bookIds(List.of(20984L))
                .build();

        BookPageResult result = repository.search(
                request,
                OpenSearchPageable.sortByLoanCount(TOP3_PAGEABLE)
        );

        List<BookDocument> bookDocuments = result.books().get().toList();

        // 블루프린트:이기적 인간은 어떻게 좋은 사회를 만드는가
        // https://product.kyobobook.co.kr/detail/S000200465020
        assertEquals("9788960519626", bookDocuments.getFirst().getIsbn13());
    }

    // author의 검색 부스트가 6 -> 7로 상향된 원인
    @Test
    void 저자가_길어도_검색결과에_포함할_수_있는_케이스() {
        BookSearchCond request = BookSearchCond.builder()
                .keyword("이도경")
                .build();

        BookPageResult result = repository.search(
                request,
                OpenSearchPageable.sortByLoanCount(TOP10_PAGEABLE)
        );

        List<BookDocument> bookDocuments = result.books().get().toList();

        assertTrue(
                bookDocuments.stream()
                        .map(BookCommonFields::getAuthor)
                        .anyMatch(author -> "마틴 클레프만 지음 ;정재부,김영준,이도경 옮김".equals(author))
        );
    }

    @Test
    void ISBN_검색() {
        BookDocument result = repository.search("9788936434120");
        assertEquals("소년이 온다",result.getTitle());
        assertEquals("한강 지음",result.getAuthor());
    }
}