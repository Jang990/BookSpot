package com.bookspot.stock.application.query;

import com.bookspot.book.domain.Book;
import com.bookspot.book.domain.BookRepository;
import com.bookspot.library.LibraryDistanceResponse;
import com.bookspot.library.infra.LibraryRepositoryForView;
import com.bookspot.stock.presentation.response.LibraryStockResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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

    @Mock BookRepository bookRepository;
    @Mock LibraryRepositoryForView libraryRepository;
    @Mock AvailableBookIdFinder availableBookIdFinder;

    @InjectMocks StockQueryService service;

    @Test
    void 요청한_책이_존재하지_않으면_예외가_발생한다() {
        when(bookRepository.findAllById(anyList()))
                .thenReturn(List.of(mock(Book.class), mock(Book.class)));

        assertThrows(IllegalArgumentException.class,
                () -> service.findLibraryStockIn5km(
                        List.of(1L, 2L, 3L),
                        new Location(0, 0)));
    }

    @Test
    void 인근_도서관이_존재하지_않으면_빈_리스트_반환() {
        when(bookRepository.findAllById(anyList()))
                .thenReturn(List.of(mock(Book.class)));
        when(libraryRepository.findLibrariesWithinRadius(anyDouble(), anyDouble(), any()))
                .thenReturn(new PageImpl<>(List.of()));

        assertEquals(List.of(), service.findLibraryStockIn5km(List.of(1L), new Location(0, 0)));
    }

    @Test
    void 재고_정보_통합() {
        Book aBook = createMockBook(1L, "A 도서");
        Book bBook = createMockBook(2L, "B 도서");
        Page<LibraryDistanceResponse> libraries = new PageImpl<>(
                List.of(new LibraryDistanceResponse(
                        1L, "A 도서관", 1234d)));

        when(bookRepository.findAllById(anyList()))
                .thenReturn(List.of(aBook, bBook));
        when(libraryRepository.findLibrariesWithinRadius(anyDouble(), anyDouble(), any()))
                .thenReturn(libraries);
        when(availableBookIdFinder.find(anyLong(), anyList()))
                .thenReturn(List.of(1L));

        LibraryStockResponse result =
                service.findLibraryStockIn5km(
                        List.of(1L, 2L), new Location(0, 0))
                .getFirst();

        assertEquals(1L, result.getLibrary().getLibraryId());
        assertEquals("A 도서관", result.getLibrary().getLibraryName());
        assertEquals(1234d, result.getLibrary().getDistanceMeter());

        assertEquals(1, result.getAvailableBooksCount());
        assertEquals(1, result.getUnavailableBooksCount());

        assertEquals(1L, result.getBookStocks().getFirst().getId());
        assertEquals("A 도서", result.getBookStocks().getFirst().getTitle());
        assertTrue(result.getBookStocks().getFirst().isAvailable());

        assertEquals(2L, result.getBookStocks().getLast().getId());
        assertEquals("B 도서", result.getBookStocks().getLast().getTitle());
        assertFalse(result.getBookStocks().getLast().isAvailable());
    }

    private Book createMockBook(long id, String title) {
        Book book = mock(Book.class);
        when(book.getId()).thenReturn(id);
        when(book.getTitle()).thenReturn(title);
        return book;
    }

}