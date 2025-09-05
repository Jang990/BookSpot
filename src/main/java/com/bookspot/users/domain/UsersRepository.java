package com.bookspot.users.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
