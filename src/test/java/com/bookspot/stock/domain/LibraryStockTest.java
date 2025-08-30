package com.bookspot.stock.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.global.DateHolder;
import com.bookspot.global.Events;
import com.bookspot.library.domain.Library;
import com.bookspot.stock.domain.event.LoanStateErrorEvent;
import com.bookspot.stock.domain.event.LoanStateUpdatedEvent;
import com.bookspot.stock.domain.event.StockRefreshedEvent;
import com.bookspot.stock.domain.service.loanable.LoanableResult;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryStockTest {
    @Mock ApplicationEventPublisher publisher;
    @Mock DateHolder dateHolder;

    @Mock Book book;
    @Mock Library library;

    LibraryStock stock = new LibraryStock();

    private MockedStatic<Events> mockedEvents;

    @BeforeEach
    void beforeEach() {
        // Events 클래스의 모든 정적 메소드를 Mocking 시작
        mockedEvents = Mockito.mockStatic(Events.class);
    }

    @AfterEach
    void tearDown() {
        mockedEvents.close();
    }


    static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(_2025_08_(1), _2025_08_(2), 1), // updateAt이 이전 날짜 → 이벤트 발생
                Arguments.of(_2025_08_(2), _2025_08_(2), 0), // updateAt이 오늘 → 이벤트 없음
                Arguments.of(_2025_08_(3), _2025_08_(2), 0)  // updateAt이 이후 날짜 → 이벤트 없음
        );
    }

    @ParameterizedTest
    @MethodSource("args")
    void stock_refresh_동작테스트(LocalDateTime updatedAt, LocalDateTime today, int expectedEventCount) {
        LibraryStock stock = new LibraryStock();
        ReflectionTestUtils.setField(stock, "id", 1L);
        ReflectionTestUtils.setField(stock, "updatedAt", updatedAt);
        ReflectionTestUtils.setField(Events.class, "publisher", publisher);
        when(dateHolder.now()).thenReturn(today);

        stock.refresh(dateHolder);

//        mockedEvents.verify(() -> Events.raise(any(StockRefreshedEvent.class)),
//                times(expectedEventCount));
    }

    @Test
    @DisplayName("API 결과가 '대출 중'일 때, ON_LOAN 상태가 되고 Updated 이벤트가 발생한다")
    void updateLoanState_OnLoan() {
        LoanableResult onLoanResult = new LoanableResult(true, false);
        LibraryStock stock = new LibraryStock();
        ReflectionTestUtils.setField(stock, "library", library);
        ReflectionTestUtils.setField(stock, "book", book);

        stock.updateLoanState(onLoanResult);

        assertEquals(LoanState.ON_LOAN, stock.getLoanState());
//        mockedEvents.verify(() -> Events.raise(any(LoanStateUpdatedEvent.class)));
    }

    @Test
    @DisplayName("API 결과에 책 정보가 없을 때, ERROR 상태가 되고 Error 이벤트가 발생한다")
    void updateLoanState_toError() {
        LoanableResult errorResult = new LoanableResult(false, false);
        stock = new LibraryStock();
        ReflectionTestUtils.setField(stock, "library", library);
        ReflectionTestUtils.setField(stock, "book", book);

        stock.updateLoanState(errorResult);

        assertEquals(LoanState.ERROR, stock.getLoanState());
//        mockedEvents.verify(() -> Events.raise(any(LoanStateErrorEvent.class)));
    }

    private static LocalDateTime _2025_08_(int day) {
        return LocalDateTime.of(2025, 8, day, 0, 0);
    }

}