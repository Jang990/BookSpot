package com.bookspot.shelves.application;

import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.presentation.dto.ShelfSummaryResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelvesQueryService {
    private final ShelvesRepository shelvesRepository;

    public ShelvesSummaryResponse findUserShelves(Long loginUserId, long shelvesOwnerUserId) {
        if(isOwner(loginUserId, shelvesOwnerUserId))
            return ShelvesDataMapper.transform(
                    shelvesRepository.findByUsersId(shelvesOwnerUserId)
            );
        else
            return ShelvesDataMapper.transform(
                    shelvesRepository.findPublicShelvesBy(shelvesOwnerUserId)
            );
    }

    private boolean isOwner(Long loginUserId, long shelvesOwnerUserId) {
        return loginUserId != null && loginUserId.equals(shelvesOwnerUserId);
    }
}
