package com.bookspot.stock.infra.api;

import com.bookspot.global.api.ApiRequester;
import com.bookspot.global.api.RequestException;
import com.bookspot.stock.domain.service.loanable.LoanStateApiClient;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import com.bookspot.stock.domain.service.loanable.exception.ApiClientException;
import com.bookspot.stock.domain.service.loanable.exception.ClientException;
import com.bookspot.stock.domain.service.loanable.exception.ServerException;
import com.bookspot.stock.domain.service.loanable.exception.TooManyRequestsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
        LoanableResponse.Response response = fetch(apiUrl);

        if(response == null)
            throw new ApiClientException("파싱 불가 오류", HttpStatus.BAD_REQUEST);

        if(StringUtils.hasText(response.getError()))
            throw new ApiClientException(response.getError(), HttpStatus.BAD_REQUEST);

        if(response.getResult() == null)
            throw new ApiClientException("파싱 불가 오류", HttpStatus.BAD_REQUEST);

        String hasBookStr = response.getResult().getHasBook();
        String loanAvailableStr = response.getResult().getLoanAvailable();

        return new LoanableResult(
                yesOrNo(hasBookStr),
                yesOrNo(loanAvailableStr)
        );
    }

    private LoanableResponse.Response fetch(String apiUrl) {
        try {
            return apiRequester.get(apiUrl, LoanableResponse.class)
                    .getResponse();
        } catch (RequestException e) {
            HttpStatusCode errorCode = e.getStatusCode();
            if(errorCode.is5xxServerError())
                throw new ServerException(errorCode);
            if(errorCode.equals(HttpStatus.TOO_MANY_REQUESTS))
                throw new TooManyRequestsException(errorCode);
            if(errorCode.is4xxClientError())
                throw new ClientException(errorCode);
            throw new ApiClientException("3xx 예상 오류", errorCode);
        }
    }

    private boolean yesOrNo(String str) {
        if(str.equals(YES))
            return true;
        if(str.equals(NO))
            return false;
        throw new IllegalArgumentException(str);
    }
}
