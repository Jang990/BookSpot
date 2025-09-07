package com.bookspot.users.domain.event;

public record BookDeletedFromBagEvent(long userId, long bookId) {
}
