package com.bookspot.stock.domain.service.loanable;

import com.bookspot.stock.domain.service.loanable.exception.LoanStateApiException;

public interface LoanStateApiClient {
    LoanableResult request(LoanableSearchCond searchCond) throws LoanStateApiException;
}
