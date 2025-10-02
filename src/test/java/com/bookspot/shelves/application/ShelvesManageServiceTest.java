package com.bookspot.shelves.application;

import com.bookspot.shelves.domain.ShelfTestHelper;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesManager;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.exception.ShelfForbiddenException;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShelvesManageServiceTest {
    ShelvesManageService service;

    @Mock UsersRepository usersRepository;
    @Mock ShelvesRepository repository;
    @Mock ShelvesManager shelvesManager;

    @BeforeEach
    void beforeEach() {
        service = new ShelvesManageService(usersRepository, repository, shelvesManager);
    }

    @Test
    void 삭제_시_owner가_일치하지_않으면_예외처리() {
        // 소유자 설정
        Users mockOwner = mock(Users.class);
        when(usersRepository.findByIdWithLock(anyLong())).thenReturn(Optional.of(mockOwner));

        // 책장 설정
        Shelves mockShelf = mock(Shelves.class);
        when(mockShelf.isOwnerBy(any())).thenReturn(false);
        when(repository.findById(anyLong())).thenReturn(Optional.of(mockShelf));

        assertThrows(
                ShelfForbiddenException.class,
                () -> service.delete(2L, 123L)
        );
    }

    @Test
    void 책장_정상_삭제() {
        // 소유자 설정
        Users mockOwner = mock(Users.class);
        when(usersRepository.findByIdWithLock(anyLong())).thenReturn(Optional.of(mockOwner));

        // 책장 설정
        Shelves shelf = ShelfTestHelper.create(mockOwner, "내책장", true);
        ReflectionTestUtils.setField(shelf, "id", 123L);
        when(repository.findById(anyLong())).thenReturn(Optional.of(shelf));

        service.delete(1L, 123L);
    }

}