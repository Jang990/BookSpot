package com.bookspot.shelves.application;

import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesManager;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ShelvesManageService {
    private final UsersRepository usersRepository;
    private final ShelvesRepository shelvesRepository;
    private final ShelvesManager shelfManager;

    public ShelfDetailResponse create(long userId, ShelfCreationRequest request) {
        Users users = usersRepository.findByIdWithLock(userId)
                .orElseThrow(UserNotFoundException::new);

        Shelves shelf = shelfManager.create(users, request);
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

    public ShelfDetailResponse update(
            long userId, long shelfId,
            ShelfCreationRequest request
    ) {
        Shelves shelf = shelvesRepository.findById(shelfId)
                .orElseThrow(ShelfNotFoundException::new);

        if (!shelf.isOwnerBy(userId))
            throw new ShelfForbiddenException(userId, shelf.getId());

        shelf.changeName(request.getName());
        if(request.getIsPublic())
            shelf.makePublic();
        else
            shelf.makePrivate();

        return ShelvesDataMapper.transform(shelf);
    }
}
