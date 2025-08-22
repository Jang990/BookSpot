package com.bookspot.book.application;

import com.bookspot.book.application.mapper.BookDataMapper;
import com.bookspot.book.infra.BookRankingDocument;
import com.bookspot.book.infra.BookRankingRepository;
import com.bookspot.book.infra.ranking.BookRankingCond;
import com.bookspot.book.infra.ranking.BookRankingResult;
import com.bookspot.book.presentation.request.BookRankingRequest;
import com.bookspot.book.presentation.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookRankingService {
    private final BookRankingRepository bookRankingRepository;

    public BookTop50RankingResponse findBooks(BookRankingRequest bookRankingRequest) {
        BookRankingResult bookRankingResult = bookRankingRepository.searchTop50(
                new BookRankingCond(
                        bookRankingRequest.period(),
                        bookRankingRequest.gender(),
                        bookRankingRequest.age())
        );

        List<BookRankPreviewResponse> list = new LinkedList<>();
        for (BookRankingDocument book : bookRankingResult.books()) {
            list.add(BookDataMapper.transform(book));
        }

        return new BookTop50RankingResponse(list);
    }
}
