package com.bookspot.stock.application.query;

import com.bookspot.stock.presentation.response.LibraryStockResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockQueryServiceTest {
    /*
    책 정보가 db 자체에 없는 경우 => 예외 발생
    인근 도서관이 없다면 => 빈 리스트 반환
    존재하지 않는 도서 정보는 unavilableBooks로 반환
     */

    @Mock AvailableBookIdFinder availableBookIdFinder;

    @InjectMocks StockQueryService service;

    @Test
    void 도서관의_책_재고현황_파악() {
        setAvailableBookIds(List.of(10L, 30L));

        LibraryStockResponse result = service.findLibraryStock(
                List.of(1L),
                List.of(10L, 20L, 30L)).getFirst();

        assertEquals(List.of(10L, 30L), result.availableBookIds());
        assertEquals(List.of(20L), result.unavailableBookIds());
        assertEquals(3, result.totalBooksCount());
    }

    @Test
    void 여러_도서관의_재고현황_파악() {
        setAvailableBookIds(
                1L,
                List.of(10L, 20L, 30L),
                List.of(10L, 30L)
        );

        setAvailableBookIds(
                2L,
                List.of(10L, 20L, 30L),
                List.of(30L)
        );

        List<LibraryStockResponse> result = service.findLibraryStock(
                List.of(1L, 2L),
                List.of(10L, 20L, 30L));

        assertEquals(List.of(10L, 30L), result.getFirst().availableBookIds());
        assertEquals(List.of(20L), result.getFirst().unavailableBookIds());
        assertEquals(3, result.getFirst().totalBooksCount());

        assertEquals(List.of(30L), result.getLast().availableBookIds());
        assertEquals(List.of(10L, 20L), result.getLast().unavailableBookIds());
        assertEquals(3, result.getLast().totalBooksCount());
    }

    private void setAvailableBookIds(List<Long> ids) {
        when(availableBookIdFinder.find(anyLong(), anyList()))
                .thenReturn(ids);
    }

    private void setAvailableBookIds(
            long libraryIds,
            List<Long> bookIds,
            List<Long> availableBookIds) {
        when(availableBookIdFinder.find(libraryIds, bookIds))
                .thenReturn(availableBookIds);
    }

}