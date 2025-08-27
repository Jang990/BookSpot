package com.bookspot.stock.infra.api;

import com.bookspot.global.NaruApiUrlHolder;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaruLoanStateApiUrlBuilder {
    private final NaruApiUrlHolder apiUrlHolder;

    protected String build(LoanableSearchCond loanableSearchCond) {
        return apiUrlHolder.getLoanableApi()
                + "&isbn13=" + loanableSearchCond.isbn13()
                + "&libCode=" + loanableSearchCond.libraryCode();
    }
    
}
