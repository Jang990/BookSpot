package com.bookspot.stock.domain.service.loanable;

public interface LoanStateApiClient {
    LoanableResult request(LoanableSearchCond searchCond);
}
