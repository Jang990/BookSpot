package com.bookspot.users.domain;

public enum OAuthProvider {
    GOOGLE;

    public static OAuthProvider fromString(String role) {
        if (role == null)
            throw new IllegalArgumentException("Role 변경 시 null 불가능");

        try {
            return OAuthProvider.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 Role String : " + role);
        }
    }
}
