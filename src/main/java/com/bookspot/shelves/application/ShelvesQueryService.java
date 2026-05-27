package com.bookspot.shelves.application;

import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.exception.ShelfNotFoundException;
import com.bookspot.shelves.domain.exception.ShelfPrivateAccessException;
import com.bookspot.shelves.infra.ShelvesPreviewQueryRepository;
import com.bookspot.shelves.infra.ShelvesQuerydslRepository;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelvesBookStatusResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelvesQueryService {
    public static final int THUMBNAIL_BOOK_COUNT = 3;
    public static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 50);

    private final ShelvesRepository shelvesRepository;
    private final ShelvesDataMapper shelvesDataMapper;

    private final ShelvesQuerydslRepository shelvesQuerydslRepository;
    private final ShelvesPreviewQueryRepository shelvesPreviewQueryRepository;

    public ShelvesSummaryResponse findPublicShelves(Pageable pageable) {
        return shelvesPreviewQueryRepository.findAllShelves(pageable, THUMBNAIL_BOOK_COUNT);
    }

        public ShelvesSummaryResponse findUserShelves(Long loginUserId, long shelvesOwnerUserId) {
        if(loginUserId != null && loginUserId.equals(loginUserId))
            return shelvesPreviewQueryRepository.findAllShelves(DEFAULT_PAGEABLE, shelvesOwnerUserId, THUMBNAIL_BOOK_COUNT);
        else
            return shelvesPreviewQueryRepository.findPublicShelves(DEFAULT_PAGEABLE, shelvesOwnerUserId, THUMBNAIL_BOOK_COUNT);
    }

    public ShelfDetailResponse findShelfDetail(Long loginUserId, long shelfId) {
        Shelves shelf = shelvesRepository.findDetailById(shelfId)
                .orElseThrow(ShelfNotFoundException::new);

        if(shelf.isPublic())
            return shelvesDataMapper.transform(shelf, shelf.getShelfBooks(), shelf.getUsers());

        if(loginUserId == null || !shelf.isOwnerBy(loginUserId))
            throw new ShelfPrivateAccessException(shelfId);
        else
            return shelvesDataMapper.transform(shelf, shelf.getShelfBooks(), shelf.getUsers());
    }

    public ShelvesBookStatusResponse findBookStatus(long loginUserId, long bookId) {
        return new ShelvesBookStatusResponse(
                shelvesQuerydslRepository.findShelvesStatus(loginUserId, bookId)
        );
    }
}
