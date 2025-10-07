package com.bookspot.shelves.application;

import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.exception.ShelfNotFoundException;
import com.bookspot.shelves.domain.exception.ShelfPrivateAccessException;
import com.bookspot.shelves.infra.ShelvesQuerydslRepository;
import com.bookspot.shelves.presentation.dto.ShelfBookStatusResponse;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelvesBookStatusResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import com.bookspot.users.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelvesQueryService {
    private final UsersRepository usersRepository;
    private final ShelvesRepository shelvesRepository;
    private final ShelvesDataMapper shelvesDataMapper;

    private final ShelvesQuerydslRepository shelvesQuerydslRepository;

    public ShelvesSummaryResponse findUserShelves(Long loginUserId, long shelvesOwnerUserId) {
        Users shelvesOwner = usersRepository.findById(shelvesOwnerUserId)
                .orElseThrow(UserNotFoundException::new);

        List<Shelves> shelves;
        if(shelvesOwner.getId().equals(loginUserId))
            shelves = shelvesRepository.findByUsersId(shelvesOwnerUserId);
        else
            shelves = shelvesRepository.findPublicShelvesBy(shelvesOwnerUserId);
        return shelvesDataMapper.transform(shelves);
    }

    public ShelfDetailResponse findShelfDetail(Long loginUserId, long shelfId) {
        Shelves shelf = shelvesRepository.findDetailById(shelfId)
                .orElseThrow(ShelfNotFoundException::new);

        if(shelf.isPublic())
            return shelvesDataMapper.transform(shelf, shelf.getShelfBooks());

        if(loginUserId == null || !shelf.isOwnerBy(loginUserId))
            throw new ShelfPrivateAccessException(shelfId);
        else
            return shelvesDataMapper.transform(shelf, shelf.getShelfBooks());
    }

    public ShelvesBookStatusResponse findBookStatus(long loginUserId, long bookId) {
        return new ShelvesBookStatusResponse(
                shelvesQuerydslRepository.findShelvesStatus(loginUserId, bookId)
        );
    }
}
