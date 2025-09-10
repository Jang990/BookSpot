package com.bookspot.stock.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.global.DateHolder;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.domain.exception.LibraryNotSupportsLoanStatusException;
import com.bookspot.stock.domain.exception.LibraryStockAlreadyRefreshedException;
import com.bookspot.stock.domain.exception.LibraryStockMismatchException;
import com.bookspot.stock.domain.service.loanable.LoanStateApiClient;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import com.bookspot.stock.domain.service.loanable.LoanableSearchCond;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanStateRefreshServiceTest {

    @Mock
    private LoanStateApiClient loanStateApiClient;

    @Mock
    private DateHolder dateHolder;

    @InjectMocks
    private LoanStateRefreshService loanStateRefreshService;

    @Test
    void Refresh_허용여부_파악_시_도서관이_대출_현황을_지원하지_않으면_예외처리() {
        // given
        Library library = mock(Library.class);
        LibraryStock stock = mock(LibraryStock.class);
        when(library.isSupportsLoanStatus()).thenReturn(false);

        // when, then
        assertThrows(LibraryNotSupportsLoanStatusException.class, () ->
                loanStateRefreshService.checkRefreshAllowed(library, stock));
    }

    @Test
    void Refresh_허용여부_파악_시_재고가_이미_Refresh됐다면_예외처리() {
        // given
        Library library = mock(Library.class);
        LibraryStock stock = mock(LibraryStock.class);
        when(library.isSupportsLoanStatus()).thenReturn(true);
        when(stock.isAlreadyRefreshed(dateHolder)).thenReturn(true);

        // when, then
        assertThrows(LibraryStockAlreadyRefreshedException.class, () ->
                loanStateRefreshService.checkRefreshAllowed(library, stock));
    }

    @Test
    void Refresh_허용여부_파악_시_도서관이_대출현황을_지원하고_Refresh되지_않았다면_허용() {
        // given
        Library library = mock(Library.class);
        LibraryStock stock = mock(LibraryStock.class);
        when(library.isSupportsLoanStatus()).thenReturn(true);
        when(stock.isAlreadyRefreshed(dateHolder)).thenReturn(false);

        // when, then
        loanStateRefreshService.checkRefreshAllowed(library, stock);
    }


    @Test
    void refresh_요청된_book_library가_stock과_불일치하면_예외발생() {
        // given
        Book book = mock(Book.class);
        Library library = mock(Library.class);
        LibraryStock stock = mock(LibraryStock.class);

        when(stock.matches(any(), any())).thenReturn(false);

        // when / then
        assertThrows(LibraryStockMismatchException.class,
                () -> loanStateRefreshService.refresh(stock, book, library));
    }

    @Test
    void refresh_이미_refreshed된_경우_기존_LoanState_반환() {
        // given
        Book book = mock(Book.class);
        Library library = mock(Library.class);
        LibraryStock stock = mock(LibraryStock.class);
        LoanState loanState = mock(LoanState.class);

        when(stock.matches(book, library)).thenReturn(true);
        when(stock.isAlreadyRefreshed(dateHolder)).thenReturn(true);
        when(stock.getLoanState()).thenReturn(loanState);

        // when
        LoanState result = loanStateRefreshService.refresh(stock, book, library);

        // then
        assertEquals(result, loanState);
        verifyNoInteractions(loanStateApiClient); // API 안 불려야 함
    }

    @Test
    void refresh_API_호출_후_stock이_업데이트되고_LoanState_반환() {
        // given
        Book book = mock(Book.class);
        when(book.getIsbn13()).thenReturn("123");

        Library library = mock(Library.class);
        when(library.getLibraryCode()).thenReturn("L001");

        LibraryStock stock = mock(LibraryStock.class);
        LoanState loanState = mock(LoanState.class);

        when(stock.matches(book, library)).thenReturn(true);
        when(stock.isAlreadyRefreshed(dateHolder)).thenReturn(false);
        when(stock.getLoanState()).thenReturn(loanState);

        LoanableResult result = mock(LoanableResult.class);
        when(loanStateApiClient.request(any())).thenReturn(result);

        // when
        LoanState actual = loanStateRefreshService.refresh(stock, book, library);

        // then
        assertEquals(actual, loanState);
        verify(loanStateApiClient).request(any(LoanableSearchCond.class));
        verify(stock).updateLoanState(result);
    }

}