package com.bookspot.shelves.domain;

import com.bookspot.global.Events;
import com.bookspot.shelves.domain.event.AddedBookToShelvesEvent;
import com.bookspot.shelves.domain.event.RemovedBookToShelvesEvent;
import com.bookspot.shelves.domain.exception.ShelfAlreadyEmptyException;
import com.bookspot.shelves.domain.exception.ShelfBookFullException;
import com.bookspot.shelves.domain.exception.ShelfForbiddenException;
import com.bookspot.shelves.presentation.dto.request.ShelfCreationRequest;
import com.bookspot.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelvesManager {
    public Shelves create(Users users, ShelfCreationRequest request) {
        users.increaseShelfSize();
        return new Shelves(users, request.getName(), request.getIsPublic());
    }

    public void addBookToShelves(List<Shelves> shelves, long userId, long bookId) {
        shelves.forEach(shelf -> {
            if(!shelf.isOwnerBy(userId))
                throw new ShelfForbiddenException(userId, shelf.getId());
        });
        shelves.forEach(shelf -> {
            if(shelf.isFull())
                throw new ShelfBookFullException(shelf.getId(), bookId);
        });

        List<Long> shelfIds = shelves.stream().map(Shelves::getId).toList();
        Events.raise(new AddedBookToShelvesEvent(shelfIds, bookId));
    }

    public void removeBookToShelves(List<Shelves> shelves, long userId, long bookId) {
        shelves.forEach(shelf -> {
            if(!shelf.isOwnerBy(userId))
                throw new ShelfForbiddenException(userId, shelf.getId());
        });

        shelves.forEach(shelf -> {
            if(shelf.isEmpty())
                throw new ShelfAlreadyEmptyException(shelf.getId());
        });

        List<Long> shelfIds = shelves.stream().map(Shelves::getId).toList();
        Events.raise(new RemovedBookToShelvesEvent(shelfIds, bookId));
    }
}
