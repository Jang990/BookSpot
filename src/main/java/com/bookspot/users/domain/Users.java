package com.bookspot.users.domain;

import com.bookspot.global.Events;
import com.bookspot.users.domain.event.BookAddedToBagEvent;
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

    private int bookBagSize = 0;

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

    public void addBookToBag(long bookId) {
        if(id == null)
            throw new IllegalStateException("저장되지 않은 사용자는 책 가방을 이용할 수 없음");
        if(bookBagSize >= UsersConst.MAX_BAG_SIZE)
            throw new IllegalStateException("책 가방 최대 사이즈를 초과함");
        Events.raise(new BookAddedToBagEvent(id, bookId));
        bookBagSize++;
    }
}
