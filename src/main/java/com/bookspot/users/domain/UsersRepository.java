package com.bookspot.users.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByProviderAndProviderId(OAuthProvider provider, String providerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM Users u WHERE u.id = :userId")
    Optional<Users> findByIdWithLock(@Param("userId") long userId);
}
