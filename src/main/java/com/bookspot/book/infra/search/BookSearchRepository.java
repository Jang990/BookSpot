package com.bookspot.book.infra.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookSearchRepository {

    Page<BookDocument> search(String keyword, Pageable pageable);


    Page<BookDocument> search(String keyword, List<Long> ids, Pageable pageable);
}
