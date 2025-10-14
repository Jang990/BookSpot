package com.bookspot.shelves.domain;

import com.bookspot.users.domain.Users;

public class ShelfTestHelper {
    public static Shelves create(Users users, String name, boolean isPublic) {
        return new Shelves(users, name, isPublic);
    }
}
