package com.bookspot.stock.infra.api;

import com.bookspot.global.ApiRequester;
import com.bookspot.global.NaruApiUrlHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NaruLoanStateApiClientTest {
    @Mock NaruApiUrlHolder apiUrlHolder;

    NaruLoanStateApiClient requester;

    String testUrl = "";

    @BeforeEach
    public void beforeEach() {
        when(apiUrlHolder.getLoanableApi()).thenReturn(testUrl);
        requester = new NaruLoanStateApiClient(
                new NaruLoanStateApiUrlBuilder(apiUrlHolder),
                new ApiRequester()
        );
    }

//    @Test
    void 정상_처리() {
        LoanableResult result = requester.request(
                new LoanableSearchCond(
                        "123003", "9788932473901"
                )
        );
        assertEquals(result.hasBook(), true);
        assertEquals(result.isLoanable(), true);

        result = requester.request(
                new LoanableSearchCond(
                        "123003", "9788936433598"
                )
        );
        assertEquals(result.hasBook(), true);
        assertEquals(result.isLoanable(), false);
    }

//    @Test
    void 잘못된_ISBN13() {
        assertThrows(IllegalStateException.class, () -> {
            requester.request(
                    new LoanableSearchCond(
                            "123003", ""
                    )
            );
        });
    }

//    @Test
    void 잘못된_도서관_코드() {
        assertThrows(IllegalStateException.class, () -> {
            requester.request(
                    new LoanableSearchCond(
                            "", "9788932473901"
                    )
            );
        });
    }
}