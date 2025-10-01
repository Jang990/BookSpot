package com.bookspot.shelves.application;

import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import com.bookspot.users.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelvesQueryService {
    private final UsersRepository usersRepository;
    private final ShelvesRepository shelvesRepository;

    public ShelvesSummaryResponse findUserShelves(Long loginUserId, long shelvesOwnerUserId) {
        Users shelvesOwner = usersRepository.findById(shelvesOwnerUserId)
                .orElseThrow(UserNotFoundException::new);

        if(shelvesOwner.getId().equals(loginUserId))
            return ShelvesDataMapper.transform(
                    shelvesRepository.findByUsersId(shelvesOwnerUserId)
            );
        else
            return ShelvesDataMapper.transform(
                    shelvesRepository.findPublicShelvesBy(shelvesOwnerUserId)
            );
    }
}
