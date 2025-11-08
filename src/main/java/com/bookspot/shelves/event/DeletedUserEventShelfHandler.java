package com.bookspot.shelves.event;

import com.bookspot.bag.domain.BagBookRepository;
import com.bookspot.shelfbooks.domain.ShelfBookRepository;
import com.bookspot.shelves.domain.Shelves;
import com.bookspot.shelves.domain.ShelvesRepository;
import com.bookspot.users.domain.event.DeletedUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletedUserEventShelfHandler {
    private final ShelvesRepository shelvesRepository;
    private final ShelfBookRepository shelfBookRepository;

    @EventListener(DeletedUserEvent.class)
    public void handle(DeletedUserEvent event) {
        List<Shelves> shelves = shelvesRepository.findByUsersId(event.userId());
        if(shelves.isEmpty())
            return;
        List<Long> shelfIds = shelves.stream().map(Shelves::getId).toList();
        shelfBookRepository.deleteByShelfIds(shelfIds);
        shelvesRepository.deleteAllByIdInBatch(shelfIds);
    }
}
