package com.bookspot.shelves.application;

import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.exception.ShelfNotFoundException;
import com.bookspot.shelves.domain.exception.ShelfPrivateAccessException;
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

    public ShelvesSummaryResponse findUserShelves(Long loginUserId, long shelvesOwnerUserId) {
        Users shelvesOwner = usersRepository.findById(shelvesOwnerUserId)
                .orElseThrow(UserNotFoundException::new);

        if(shelvesOwner.getId().equals(loginUserId))
            return shelvesDataMapper.transform(
                    shelvesRepository.findByUsersId(shelvesOwnerUserId)
            );
        else
            return shelvesDataMapper.transform(
                    shelvesRepository.findPublicShelvesBy(shelvesOwnerUserId)
            );
    }

    public ShelfDetailResponse findShelfDetail(Long loginUserId, long shelfId) {
        Shelves shelf = shelvesRepository.findWithUser(shelfId)
                .orElseThrow(ShelfNotFoundException::new);

        if(shelf.isPublic())
            return shelvesDataMapper.transform(shelf);

        if(loginUserId == null || !shelf.isOwnerBy(loginUserId))
            throw new ShelfPrivateAccessException(shelfId);
        else
            return shelvesDataMapper.transform(shelf);
    }

    public ShelvesBookStatusResponse findBookStatus(long loginUserId, long bookId) {
        return new ShelvesBookStatusResponse(
                List.of(
                        new ShelfBookStatusResponse(1, "추천하는 책", true, false),
                        new ShelfBookStatusResponse(2, "책장 A", false, true),
                        new ShelfBookStatusResponse(3, "개발 필독서", true, false),
                        new ShelfBookStatusResponse(4, "나중에 볼 책", false, true),
                        new ShelfBookStatusResponse(11, "영향을 받은 책", true, false)
                )
        );
    }
}
