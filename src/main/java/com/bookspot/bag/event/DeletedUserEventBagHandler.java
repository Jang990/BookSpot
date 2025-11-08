package com.bookspot.bag.event;

import com.bookspot.bag.domain.BagBookRepository;
import com.bookspot.users.domain.event.DeletedUserEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletedUserEventBagHandler {
    private final BagBookRepository bagBookRepository;

    @EventListener(DeletedUserEvent.class)
    public void handle(DeletedUserEvent event) {
        bagBookRepository.deleteByUsersId(event.userId());
    }
}
