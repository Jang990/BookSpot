package com.bookspot.shelves.application;

import com.bookspot.shelfbooks.domain.ShelfBook;
import com.bookspot.shelves.application.mapper.ShelvesDataMapper;
import com.bookspot.book.application.BookIsbnService;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.shelves.domain.exception.ShelfNotFoundException;
import com.bookspot.shelves.domain.exception.ShelfPrivateAccessException;
import com.bookspot.shelves.infra.ShelvesQuerydslRepository;
import com.bookspot.shelves.presentation.dto.ShelfDetailResponse;
import com.bookspot.shelves.presentation.dto.ShelvesBookStatusResponse;
import com.bookspot.shelves.presentation.dto.ShelvesSummaryResponse;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import com.bookspot.users.domain.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShelvesQueryService {
    public static final int THUMBNAIL_BOOK_COUNT = 3;

    private final UsersRepository usersRepository;
    private final ShelvesRepository shelvesRepository;
    private final ShelvesDataMapper shelvesDataMapper;
    private final BookIsbnService bookIsbnService;

    private final ShelvesQuerydslRepository shelvesQuerydslRepository;

    public ShelvesSummaryResponse findPublicShelves(Pageable pageable) {
        List<Shelves> shelves = shelvesRepository.findPublicShelvesBy(pageable);

        List<ShelfBook> shelfBooks = shelves.stream()
                .map(Shelves::getShelfBooks)
                .flatMap(sb -> sb.stream().limit(THUMBNAIL_BOOK_COUNT))
                .toList();

        Map<Long, String> bookIdAndIsbn13 = bookIsbnService.findBookIsbn(shelfBooks);
        return shelvesDataMapper.transform(shelves, bookIdAndIsbn13);
    }

    public ShelvesSummaryResponse findUserShelves(Long loginUserId, long shelvesOwnerUserId) {
        Users shelvesOwner = usersRepository.findById(shelvesOwnerUserId)
                .orElseThrow(UserNotFoundException::new);

        List<Shelves> shelves;
        if(shelvesOwner.getId().equals(loginUserId))
            shelves = shelvesRepository.findByUsersId(shelvesOwnerUserId);
        else
            shelves = shelvesRepository.findPublicShelvesBy(shelvesOwnerUserId);

        List<ShelfBook> shelfBooks = shelves.stream()
                .map(Shelves::getShelfBooks)
                .flatMap(sb -> sb.stream().limit(THUMBNAIL_BOOK_COUNT))
                .toList();

        Map<Long, String> bookIdAndIsbn13 = bookIsbnService.findBookIsbn(shelfBooks);
        return shelvesDataMapper.transform(shelves, bookIdAndIsbn13);
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
