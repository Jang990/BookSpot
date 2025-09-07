package com.bookspot.bag.application;

import com.bookspot.bag.domain.BagBook;
import com.bookspot.bag.domain.BagBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BagBookService {
    private final BagBookRepository bagBookRepository;

    public List<Long> findBookIds(long userId) {
        return bagBookRepository.findByUsersId(userId).stream()
                .map(bag -> bag.getBook().getId())
                .toList();
    }
}
