package com.bookspot.shelves.application;

import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.exception.ShelfForbiddenException;
import com.bookspot.shelves.domain.exception.ShelfNotFoundException;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.request.ShelfCreationRequest;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import com.bookspot.users.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelvesManageService {
    private final UsersRepository usersRepository;
    private final ShelvesRepository shelvesRepository;

    public ShelfDetailResponse create(long userId, ShelfCreationRequest request) {
        // TODO: 사용자가 책장을 얼마나 만들었는지 검증 필요
        Users users = usersRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Shelves shelf = new Shelves(users, request.getName(), request.getIsPublic());
        shelvesRepository.save(shelf);

        return ShelvesDataMapper.transform(shelf);
    }

    public void delete(long userId, long shelfId) {
        Shelves shelf = shelvesRepository.findById(shelfId)
                .orElseThrow(ShelfNotFoundException::new);

        if (shelf.isOwnerBy(userId)) {
            shelvesRepository.deleteById(shelfId);
            return;
        }

        throw new ShelfForbiddenException(userId, shelf.getId());
    }
}
