package com.bookspot.book.presentation.response;

import java.util.List;

public record BookTop50RankingResponse(List<BookRankPreviewResponse> rankedBooks) {
}
