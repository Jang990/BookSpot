package com.bookspot.stock.infra.api;

import com.bookspot.global.NaruApiUrlHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NaruLoanableApiUrlBuilder {
    private final NaruApiUrlHolder apiUrlHolder;

    public String build(LoanableSearchCond loanableSearchCond) {
        return apiUrlHolder.getLoanableApi()
                + "&isbn13=" + loanableSearchCond.isbn13()
                + "&libCode=" + loanableSearchCond.libraryCode();
    }
    
}
