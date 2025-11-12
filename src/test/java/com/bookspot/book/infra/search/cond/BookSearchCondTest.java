package com.bookspot.book.infra.search.cond;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.RangeQuery;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookSearchCondTest {
    @Test
    void keyword_isbn13을_함께_넣어_boolQuery를_생성하려하면_오류() {
        BookSearchCond searchCond = BookSearchCond.builder()
                .keyword("객체")
                .isbn13("9788936434120")
                .build();

        assertThrows(IllegalArgumentException.class, searchCond::toBoolQuery);
    }

    @Test
    void 키워드_검색_정상처리() {
        BookSearchCond searchCond = BookSearchCond.builder()
                .bookIds(List.of(1L, 2L, 3L))
                .keyword("객체")
                .libraryId(1L)
                .categoryCond(BookCategoryCond.mid(412, "대수학"))
                .build();

        BoolQuery result = searchCond.toBoolQuery().bool();

        assertThat(result.filter()).hasSize(3);
        assertIdFilter(
                result.filter().get(0),
                new String[] {"1", "2", "3"}
        );
        assertTermFilter(
                result.filter().get(1),
                "library_ids", "1"
        );
        assertTermFilter(
                result.filter().get(2),
                "book_categories.mid_category", "412.대수학"
        );

        // should 검증
        assertThat(result.should()).hasSize(1);
        assertThat(result.minimumShouldMatch()).isEqualTo("1");

        List<Query> shoulds = result.should();

        assertThat(shoulds.get(0).multiMatch().fields()).isEqualTo(
                List.of(
                        "title^5",
//                        "title.ngram^6",
                        "title.ws^8",
                        "title.keyword^10",
                        "author^7",
                        "publisher^8",

                        "search_text^3",
                        "search_text.ws^5"
                )
        );
        assertThat(shoulds.get(0).multiMatch().query()).isEqualTo("객체");
    }

    @Test
    void ISBN_검색_정상처리() {
        BookSearchCond searchCond = BookSearchCond.builder()
                .libraryId(1L)
                .isbn13("9788936434120")
                .build();

        BoolQuery result = searchCond.toBoolQuery().bool();

        assertThat(result.filter()).hasSize(2);
        assertTermFilter(
                result.filter().get(0),
                "library_ids", "1"
        );
        assertTermFilter(
                result.filter().get(1),
                "isbn13", "9788936434120"
        );

        // should는 키워드 검색만 사용
        assertThat(result.should()).isEmpty();
    }

    @Test
    void Year_범위_검색() {
        BookSearchCond searchCond = BookSearchCond.builder()
                .yearRange(new YearRange(2020, 2025))
                .build();

        BoolQuery result = searchCond.toBoolQuery().bool();
        assertThat(result.filter()).hasSize(1);
        List<Query> yearCondQueries = result.filter().get(0).bool().should();

        // Year 범위 안쪽이거나 발행연도를 알 수 없으면 검색결과에 포함시킴
        assertRangeFilter(
                yearCondQueries.get(0).range(),
                "publication_year", 2020, 2025
        );
        assertEquals("publication_year", yearCondQueries.get(1).bool().mustNot().get(0).exists().field());
    }

    private void assertIdFilter(Query idFilter, String[] expectedIds) {
        assertThat(idFilter.ids().values())
                .containsExactlyInAnyOrder(expectedIds);
    }

    private void assertTermFilter(
            Query termFilter,
            String expectedFiledName,
            String expectedValue
    ) {
        assertThat(termFilter.term().field())
                .isEqualTo(expectedFiledName);

        assertThat(termFilter.term().value().stringValue())
                .isEqualTo(expectedValue);
    }

    private void assertRangeFilter(
            RangeQuery rangeQuery,
            String expectedFiledName,
            int expectedGte,
            int expectedLte
    ) {
        assertThat(rangeQuery.field())
                .isEqualTo(expectedFiledName);
        assertThat(rangeQuery.gte().to(Integer.class)).isEqualTo(expectedGte);
        assertThat(rangeQuery.lte().to(Integer.class)).isEqualTo(expectedLte);
    }

}