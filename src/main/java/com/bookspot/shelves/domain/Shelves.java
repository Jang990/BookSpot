package com.bookspot.shelves.domain;

import com.bookspot.users.domain.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Shelves {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int bookCount;
    private boolean isPublic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected Shelves(Users users, String name, boolean isPublic) {
        this.users = users;
        this.name = name;
        this.isPublic = isPublic;
        this.bookCount = 0;
    }

    public boolean isOwnerBy(long userId) {
        return users.getId().equals(userId);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void makePublic() {
        isPublic = true;
    }

    public void makePrivate() {
        isPublic = false;
    }
}
