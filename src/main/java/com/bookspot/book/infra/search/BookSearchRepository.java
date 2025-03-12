package com.bookspot.book.infra.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface BookSearchRepository extends ElasticsearchRepository<BookDocument, Long> {
    @Query("""
            {
                "bool" : {
                    "should" : [
                        { "match_phrase": { "title": "?0" } },
                        { "match_phrase": { "author": "?0" } }
                    ]
                }
            }
            """)
    Page<BookDocument> search(String keyword, Pageable pageable);

    @Query("""
            {
                "bool" : {
                    "should" : [
                        { "match_phrase": { "title": "?0" } },
                        { "match_phrase": { "author": "?0" } }
                    ],
                    "must" : {
                        "terms" : { "id" : ?1 }
                    }
                }
            }
            """)
    Page<BookDocument> search(String keyword, List<Long> ids, Pageable pageable);
}
