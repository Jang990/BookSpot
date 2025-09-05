package com.bookspot.users.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider", "providerId"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UsersRole role;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private OAuthProvider provider;

    @Column(nullable = false)
    private String providerId;

    private Users(
            String email,
            String nickname,
            UsersRole role,
            OAuthProvider provider,
            String providerId
    ) {
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static Users createUser(
            String providerId,
            OAuthProvider oAuthProvider,
            String email
    ) {
        String nickname = email;
        return new Users(
                email, nickname,
                UsersRole.USER,
                oAuthProvider, providerId
        );
    }

    public String getRole() {
        return role.toString();
    }
}
