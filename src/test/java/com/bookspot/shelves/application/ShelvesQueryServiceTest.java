package com.bookspot.shelves.application;

import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.exception.ShelfPrivateAccessException;
import com.bookspot.users.domain.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShelvesQueryServiceTest {
    @Mock UsersRepository usersRepository;
    @Mock ShelvesRepository shelvesRepository;
    @Mock ShelvesDataMapper shelvesDataMapper;

    @InjectMocks ShelvesQueryService queryService;

    @Test
    void 공개_책장_상세정보_조회는_누구나_조회가능() {
        Shelves mockShelf = mock(Shelves.class);
        when(mockShelf.isPublic()).thenReturn(true); // 공개 책장
        when(shelvesRepository.findWithUser(anyLong())).thenReturn(Optional.of(mockShelf));

        queryService.findShelfDetail(null, 1L);
        queryService.findShelfDetail(123L, 1L);
    }

    @Test
    void 비공개_책장_상세정보_조회__비로그인_사용자는_예외처리() {
        Shelves mockShelf = mock(Shelves.class);
        when(mockShelf.isPublic()).thenReturn(false); // 비공개 책장
        when(shelvesRepository.findWithUser(anyLong())).thenReturn(Optional.of(mockShelf));

        assertThrows(
                ShelfPrivateAccessException.class,
                () -> queryService.findShelfDetail(null, 1L) // 로그인하지 않은 사용자
        );
    }

    @Test
    void 비공개_책장_소유자는_상세정보_조회가능() {
        // 책장 설정
        Shelves shelf = mock(Shelves.class);
        when(shelf.isPublic()).thenReturn(false); // 비공개 책장
        when(shelf.isOwnerBy(anyLong())).thenReturn(true); // 소유자 맞음
        when(shelvesRepository.findWithUser(anyLong())).thenReturn(Optional.of(shelf));

        queryService.findShelfDetail(12L, 1L);
    }

}