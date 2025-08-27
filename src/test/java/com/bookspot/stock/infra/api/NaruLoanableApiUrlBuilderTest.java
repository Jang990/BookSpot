package com.bookspot.stock.infra.api;

import com.bookspot.global.NaruApiUrlHolder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NaruLoanableApiUrlBuilderTest {
    @Mock
    NaruApiUrlHolder apiUrlHolder;

    @InjectMocks
    NaruLoanableApiUrlBuilder apiUrlBuilder;

    @Test
    void test() {
        when(apiUrlHolder.getLoanableApi()).thenReturn("ABC?myKey=AAA");

        assertEquals(
                "ABC?myKey=AAA&isbn13=5555555555555&libCode=1234567",
                apiUrlBuilder.build(new LoanableSearchCond("1234567", "5555555555555"))
        );
    }
}