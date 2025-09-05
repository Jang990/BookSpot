package com.bookspot.users.domain;

public enum UsersRole {
    USER, ADMIN;

    public static UsersRole fromString(String role) {
        if (role == null)
            throw new IllegalArgumentException("Role 변경 시 null 불가능");

        try {
            return UsersRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 Role String : " + role);
        }
    }
}
