package com.bookspot.users.domain;

import com.bookspot.book.domain.Book;
import com.bookspot.global.Events;
import com.bookspot.users.domain.event.BookAddedToBagEvent;
import com.bookspot.users.domain.event.BookBagClearedEvent;
import com.bookspot.users.domain.event.BookDeletedFromBagEvent;
import com.bookspot.users.domain.exception.UserBagEmptyException;
import com.bookspot.users.domain.exception.UserBagFullException;
import com.bookspot.users.domain.exception.UserNotFoundException;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    public void addBookToBag(Book book) {
        if(id == null)
            throw new UserNotFoundException();
        if(bookBagSize >= UsersConst.MAX_BAG_SIZE)
            throw new UserBagFullException(id);

        bookBagSize++;
        Events.raise(new BookAddedToBagEvent(id, book.getId()));
    }

    public void deleteBookFromBag(Book book) {
        if(id == null)
            throw new UserNotFoundException();
        if(bookBagSize <= 0)
            throw new UserBagEmptyException(id);

        bookBagSize--;
        Events.raise(new BookDeletedFromBagEvent(id, book.getId()));
    }

    public void clearBag() {
        if(id == null)
            throw new UserNotFoundException();
        if(bookBagSize <= 0)
            throw new UserBagEmptyException(id);

        bookBagSize = 0;
        Events.raise(new BookBagClearedEvent(id));
    }
}
