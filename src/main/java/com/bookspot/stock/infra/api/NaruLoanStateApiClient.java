package com.bookspot.stock.infra.api;

import com.bookspot.global.ApiRequester;
import com.bookspot.stock.domain.service.loanable.LoanStateApiClient;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class NaruLoanStateApiClient implements LoanStateApiClient {
    private final NaruLoanStateApiUrlBuilder apiUrlBuilder;
    private final ApiRequester apiRequester;

    private static final String YES = "Y";
    private static final String NO = "N";

    @Override
    public LoanableResult request(LoanableSearchCond searchCond) {
        String apiUrl = apiUrlBuilder.build(searchCond);
        LoanableResponse.Response response = apiRequester.get(apiUrl, LoanableResponse.class)
                .getResponse();

        if(response == null)
            throw new IllegalStateException("파싱 불가 오류");

        if(StringUtils.hasText(response.getError()))
            throw new IllegalStateException(response.getError());

        if(response.getResult() == null)
            throw new IllegalStateException("파싱 불가 오류");

        String hasBookStr = response.getResult().getHasBook();
        String loanAvailableStr = response.getResult().getLoanAvailable();

        return new LoanableResult(
                yesOrNo(hasBookStr),
                yesOrNo(loanAvailableStr)
        );
    }

    private boolean yesOrNo(String str) {
        if(str.equals(YES))
            return true;
        if(str.equals(NO))
            return false;
        throw new IllegalArgumentException(str);
    }
}
