package com.bookspot.book.infra.search.builder;

import com.bookspot.book.infra.search.cond.BookSearchCond;
import com.bookspot.category.application.BookCategoryDto;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BookQueryBuilderTest {
    private BookQueryBuilder bookQueryBuilder = new BookQueryBuilder(new BookCategoryNameBuilder());

    @Test
    void 정상처리() {
        BookSearchCond searchCond = BookSearchCond.builder()
                .bookIds(List.of(1L, 2L, 3L))
                .keyword("객체")
                .libraryId(1L)
                .categoryFilter(new BookCategoryDto(412, "대수학"))
                .build();
        BoolQuery result = bookQueryBuilder.buildBool(searchCond).bool();

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
                "book_categories", "412.대수학"
        );

        // should 검증
        assertThat(result.should()).hasSize(4);
        assertThat(result.minimumShouldMatch()).isEqualTo("1");

        List<Query> shoulds = result.should();

        assertThat(shoulds.get(0).matchPhrase().field()).isEqualTo("title");
        assertThat(shoulds.get(0).matchPhrase().query()).isEqualTo("객체");

        assertThat(shoulds.get(1).match().field()).isEqualTo("title.ngram");
        assertThat(shoulds.get(1).match().query().stringValue()).isEqualTo("객체");

        assertThat(shoulds.get(2).matchPhrase().field()).isEqualTo("author");
        assertThat(shoulds.get(2).matchPhrase().query()).isEqualTo("객체");

        assertThat(shoulds.get(3).term().field()).isEqualTo("publisher");
        assertThat(shoulds.get(3).term().value().stringValue()).isEqualTo("객체");
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