package com.bookspot.book.infra.search.cond;

import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookSearchCondTest {
    @Test
    void 정상처리() {
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
                        "author^6",
                        "publisher^8"
                )
        );
        assertThat(shoulds.get(0).multiMatch().query()).isEqualTo("객체");

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

}