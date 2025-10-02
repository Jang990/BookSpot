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
    private final ShelvesDataMapper shelvesDataMapper;

    public ShelfDetailResponse create(long ownerUserId, ShelfCreationRequest request) {
        Users owner = usersRepository.findByIdWithLock(ownerUserId)
                .orElseThrow(UserNotFoundException::new);

        Shelves shelf = shelfManager.create(owner, request);
        shelvesRepository.save(shelf);

        return shelvesDataMapper.transform(shelf);
    }

    public void delete(long loginUserId, long shelfId) {
        Users users = usersRepository.findByIdWithLock(loginUserId)
                .orElseThrow(UserNotFoundException::new);
        Shelves shelf = shelvesRepository.findById(shelfId)
                .orElseThrow(ShelfNotFoundException::new);

        if (shelf.isOwnerBy(users)) {
            users.decreaseShelfSize();
            shelvesRepository.deleteById(shelfId);
            return;
        }

        throw new ShelfForbiddenException(loginUserId, shelf.getId());
    }

    public ShelfDetailResponse update(
            long loginUserId, long shelfId,
            ShelfCreationRequest request
    ) {
        Shelves shelf = shelvesRepository.findById(shelfId)
                .orElseThrow(ShelfNotFoundException::new);

        if (!shelf.isOwnerBy(loginUserId))
            throw new ShelfForbiddenException(loginUserId, shelf.getId());

        shelf.changeName(request.getName());
        if(request.getIsPublic())
            shelf.makePublic();
        else
            shelf.makePrivate();

        return shelvesDataMapper.transform(shelf);
    }
}
