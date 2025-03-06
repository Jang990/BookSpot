package com.bookspot.book.infra.search;

import com.bookspot.global.consts.Indices;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = Indices.BOOK_INDEX)
public class BookDocument {
    @Id
    private Long id;
    private String title;
    private String isbn13;
    private int subjectCode;
    private String author;
    private Integer publicationYear;
    private String publisher;
    private int loanCount;
}
