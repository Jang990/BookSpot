package com.bookspot.stock.domain;

import com.bookspot.global.DateHolder;
import com.bookspot.global.Events;
import com.bookspot.stock.domain.event.StockRefreshedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryStockTest {
    @Mock ApplicationEventPublisher publisher;
    @Mock DateHolder dateHolder;

    LibraryStock stock = new LibraryStock();

    static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(_2025_08_(1), _2025_08_(2), 1), // updateAt이 이전 날짜 → 이벤트 발생
                Arguments.of(_2025_08_(2), _2025_08_(2), 0), // updateAt이 오늘 → 이벤트 없음
                Arguments.of(_2025_08_(3), _2025_08_(2), 0)  // updateAt이 이후 날짜 → 이벤트 없음
        );
    }

    @ParameterizedTest
    @MethodSource("args")
    void stock_refresh_동작테스트(LocalDate updatedAt, LocalDate today, int expectedEventCount) {
        // given
        LibraryStock stock = new LibraryStock();
        ReflectionTestUtils.setField(stock, "id", 1L);
        ReflectionTestUtils.setField(stock, "updatedAt", updatedAt);
        ReflectionTestUtils.setField(Events.class, "publisher", publisher);
        when(dateHolder.now()).thenReturn(today);

        // when
        stock.refresh(dateHolder);

        // then
        verify(publisher, times(expectedEventCount)).publishEvent(any(StockRefreshedEvent.class));
    }



    private static LocalDate _2025_08_(int day) {
        return LocalDate.of(2025, 8, day);
    }

}