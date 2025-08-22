package com.bookspot.book.infra;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDocument extends BookCommonFields {
    @JsonProperty("loan_count")
    private int loanCount;
    @JsonProperty("library_ids")
    private List<Long> libraryIds;

    public LocalDate getCreatedAtDate() {
        return LocalDate.parse(getCreatedAt());
    }
}
