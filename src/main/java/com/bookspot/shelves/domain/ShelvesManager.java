package com.bookspot.shelves.domain;

import com.bookspot.shelves.presentation.dto.request.ShelfCreationRequest;
import com.bookspot.users.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShelvesManager {
    public Shelves create(Users users, ShelfCreationRequest request) {
        users.increaseShelfSize();
        return new Shelves(users, request.getName(), request.getIsPublic());
    }
}
