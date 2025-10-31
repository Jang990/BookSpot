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

    /*
        "title^5",
        "title.ws^8",
        "title.keyword^10",
        "author^7",
        "publisher^8"

        이렇게 검색할 때 2025-09에서는 4156건이 나왔다.

        제목+저자, 제목+출판사, ... 등의 검색을 위해
        search_text 필드가 추가됐고, 그에 따라 검색 범위가 넓어졌지만
        너무 넓어지진 않게 제한하는 테스트 코드

        매월 세계사 관련 책이 늘어나면서 4500건을 초과할 수도 있음.
     */
    @Test
    void 세계사_검색결과가_과하게_늘어나지_않도록_주의() {
        BookSearchCond request = BookSearchCond.builder()
                .keyword("세계사")
                .build();

        BookPageResult result = repository.search(
                request,
                OpenSearchPageable.sortByLoanCount(TOP10_PAGEABLE)
        );

        assertTrue(
                4000 < result.books().getTotalElements()
                        && result.books().getTotalElements() < 4500
        );
    }

    @Test
    void 제목_저자__검색__세계사_임소미() {
        BookSearchCond request = BookSearchCond.builder()
                .keyword("세계사 임소미")
                .build();

        BookPageResult result = repository.search(
                request,
                OpenSearchPageable.sortByLoanCount(TOP10_PAGEABLE)
        );

        List<BookDocument> books = result.books().get().toList();

        // 요즘 어른을 위한 최소한의 세계사 - 임소미 저자(글) · 김봉중 감수
        // https://product.kyobobook.co.kr/detail/S000209045024
        assertEquals("9791193128428", books.getFirst().getIsbn13());

        // 요즘 어른을 위한 최소한의 세계사(큰글자도서)
        // https://product.kyobobook.co.kr/detail/S000212194834
        assertEquals("9791193128701", books.get(1).getIsbn13());
    }

    @Test
    void 제목_출판사__검색__셜록_열림원() {
        BookSearchCond request = BookSearchCond.builder()
                .keyword("셜록 열림원")
                .build();

        BookPageResult result = repository.search(
                request,
                OpenSearchPageable.sortByLoanCount(TOP10_PAGEABLE)
        );

        List<BookDocument> books = result.books().get().toList();

        // [국내도서] 셜록 1: 주홍색 연구 아서 코넌 도일 저자(글)  | 최현빈 번역
        // https://product.kyobobook.co.kr/detail/S000001909416
        assertEquals("9791188047154", books.getFirst().getIsbn13());

        // [국내도서] 셜록 2: 바스커빌의 사냥개
        // https://product.kyobobook.co.kr/detail/S000001909415
        assertEquals("9791188047147", books.get(1).getIsbn13());
    }
}