package com.bookspot.book.infra.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookCategories {

    @JsonProperty("top_category")
    private String topCategory;

    @JsonProperty("mid_category")
    private String midCategory;

    @JsonProperty("leaf_category")
    private String leafCategory;

}
