package com.bookspot.stock.domain.service.loanable;

import com.bookspot.stock.domain.service.loanable.exception.ApiClientException;

public interface LoanStateApiClient {
    LoanableResult request(LoanableSearchCond searchCond) throws ApiClientException;
}
