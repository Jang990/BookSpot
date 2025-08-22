package com.bookspot.book.presentation;

import com.bookspot.book.infra.RankingAge;
import com.bookspot.book.infra.RankingGender;
import com.bookspot.book.infra.RankingPeriod;
import com.bookspot.book.presentation.request.BookRankingRequest;
import com.bookspot.book.presentation.response.BookTop50RankingResponse;
import com.bookspot.global.log.BasicLog;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@BasicLog
@RestController
@RequiredArgsConstructor
public class BookRankingController {
    @GetMapping("/api/books/rankings")
    public ResponseEntity<BookTop50RankingResponse> findRankedBooks(
            @Valid BookRankingRequest rankingRequest
    ) {
        return ResponseEntity.ok(new BookTop50RankingResponse(List.of()));
    }
}
