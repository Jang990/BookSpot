package com.bookspot.book.infra.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookSearchRepository extends ElasticsearchRepository<BookDocument, Long> {
    @Query("""
            {
                "bool" : {
                    "should" : [
                        { "match" : { "title" : "?0" } },
                        { "match" : { "author" : "?0" } }
                    ]
                }
            }
            """)
    Page<BookDocument> findWithKeyword(String keyword, Pageable pageable);
}
