package com.bookspot.shelves.domain;

import com.bookspot.EventMockingHelper;
import com.bookspot.shelves.domain.exception.ShelfAlreadyEmptyException;
import com.bookspot.shelves.domain.exception.ShelfBookFullException;
import com.bookspot.shelves.domain.exception.ShelfForbiddenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
class ShelvesManagerTest {
    ShelvesManager shelvesManager = new ShelvesManager();

    @Test
    void addBookToShelvesbulk_save_정상처리() {
        EventMockingHelper.runWithoutEvents(() -> {

            List<Shelves> shelves = List.of(
                    emptyOwnShelf(),
                    emptyOwnShelf(),
                    emptyOwnShelf()
            );
            shelvesManager.addBookToShelves(shelves, 1L, 1L);
        });
    }

    @Test
    void addBookToShelves_소유하지_않은_책장_포함시_예외처리() {
        List<Shelves> shelves = List.of(
                emptyOwnShelf(),
                mockShelf(false, false, false),
                emptyOwnShelf()
        );
        Assertions.assertThrows(ShelfForbiddenException.class,
                () -> shelvesManager.addBookToShelves(shelves, 1L, 1L));
    }

    @Test
    void addBookToShelves_이미_가득한_책장_포함시_예외처리() {
        List<Shelves> shelves = List.of(
                emptyOwnShelf(),
                mockShelf(true, true, false),
                emptyOwnShelf()
        );
        Assertions.assertThrows(ShelfBookFullException.class,
                () -> shelvesManager.addBookToShelves(shelves, 1L, 1L));
    }

    @Test
    void removeBookToShelves_정상처리() {
        EventMockingHelper.runWithoutEvents(() -> {
            List<Shelves> shelves = List.of(
                    filledOwnShelf(),
                    filledOwnShelf(),
                    filledOwnShelf()
            );
            shelvesManager.removeBookToShelves(shelves, 1L, 1L);
        });
    }

    @Test
    void removeBookToShelves_소유주_확인_시_예외() {
        List<Shelves> shelves = List.of(
                emptyOwnShelf(),
                mockShelf(false, false, false),
                emptyOwnShelf()
        );
        Assertions.assertThrows(ShelfForbiddenException.class,
                () -> shelvesManager.removeBookToShelves(shelves, 1L, 1L));
    }

    @Test
    void removeBookToShelves_비어있는_책장에서_책을_지우려_시도하면_예외() {
        List<Shelves> shelves = List.of(
                filledOwnShelf(),
                emptyOwnShelf(),
                filledOwnShelf()
        );
        Assertions.assertThrows(ShelfAlreadyEmptyException.class,
                () -> shelvesManager.removeBookToShelves(shelves, 1L, 1L));
    }


    private Shelves emptyOwnShelf() {
        return mockShelf(true, false, true);
    }

    private Shelves filledOwnShelf() {
        return mockShelf(true, false, false);
    }

    private Shelves mockShelf(boolean isOwnerBy, boolean isFull, boolean isEmpty) {
        Shelves shelf = mock(Shelves.class);
        when(shelf.isOwnerBy(anyLong())).thenReturn(isOwnerBy);
        when(shelf.isFull()).thenReturn(isFull);
        when(shelf.isEmpty()).thenReturn(isEmpty);
        return shelf;
    }

}