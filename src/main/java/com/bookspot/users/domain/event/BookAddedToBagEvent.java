package com.bookspot.users.domain.event;

public record BookAddedToBagEvent(long userId, long bookId) {
}
