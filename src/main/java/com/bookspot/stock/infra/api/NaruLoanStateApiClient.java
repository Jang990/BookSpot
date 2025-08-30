package com.bookspot.stock.infra.api;

import com.bookspot.global.api.ApiRequester;
import com.bookspot.global.api.RequestException;
import com.bookspot.stock.domain.service.loanable.LoanStateApiClient;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import com.bookspot.stock.domain.service.loanable.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
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
            throw new LoanStateApiException("파싱 불가 오류");

        if (StringUtils.hasText(response.getError())) {
            log.warn("{} 정보나루 API 요청 시 오류 발생 => {}", searchCond, response.getError());
            throw new LoanStateApiException("파싱 불가 오류");
        }

        if(response.getResult() == null)
            throw new LoanStateApiException("파싱 불가 오류");

        String hasBookStr = response.getResult().getHasBook();
        String loanAvailableStr = response.getResult().getLoanAvailable();

        LoanableResult loanableResult = new LoanableResult(
                yesOrNo(hasBookStr),
                yesOrNo(loanAvailableStr)
        );
        if(loanableResult.hasBook())
            log.info("정보나루 API 활용 : {} => {}", searchCond, loanableResult);
        else
            log.warn("정보나루 API : 도서관에 책이 존재 X. {} => {}", searchCond, loanableResult);
        return loanableResult;
    }

    private LoanableResponse.Response fetch(String apiUrl) {
        try {
            return apiRequester.get(apiUrl, LoanableResponse.class)
                    .getResponse();
        } catch (RequestException e) {
            HttpStatusCode errorCode = e.getStatusCode();
            if(errorCode.is5xxServerError() || errorCode.equals(HttpStatus.TOO_MANY_REQUESTS))
                throw new RetryableLoanStateApiException(errorCode + "오류 발생으로 이후 재시도 요청 필요");
            throw new LoanStateApiException(errorCode + " 오류로 설계 변경 필요");
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
