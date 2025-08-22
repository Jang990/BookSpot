package com.bookspot.book.infra.ranking;

import com.bookspot.book.infra.BookRankingDocument;
import com.bookspot.book.infra.BookRankingRepository;
import com.bookspot.book.infra.search.pagination.OpenSearchPageable;
import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookOpenSearchRankingRepository implements BookRankingRepository {
    private static final Pageable TOP_50 = PageRequest.of(0, 50);

    private final OpenSearchClient client;
    private final BookRankingRequestBuilder rankingRequestBuilder;

    @Override
    public BookRankingResult searchTop50(BookRankingCond rankingCond) {
        if(rankingCond == null)
            throw new IllegalArgumentException("필수 조건 누락");
        OpenSearchPageable openSearchPageable = OpenSearchPageable.withRank(TOP_50);

        try {
            SearchResponse<BookRankingDocument> resp = client.search(
                    rankingRequestBuilder.build(
                            rankingCond.toBoolQuery(),
                            openSearchPageable
                    ),
                    BookRankingDocument.class
            );

            return createPageResult(resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private BookRankingResult createPageResult(SearchResponse<BookRankingDocument> resp) {
        List<BookRankingDocument> list = resp.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());

        long total = resp.hits().total().value();
        return new BookRankingResult(
                new PageImpl<>(list, TOP_50, total)
        );
    }
}
