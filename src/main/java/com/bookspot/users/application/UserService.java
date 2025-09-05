package com.bookspot.users.application;

import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    public UserDto createOrFindUser(String email, String provider, String providerId) {
        OAuthProvider oAuthProvider = OAuthProvider.fromString(provider);
        Users users = usersRepository.findByProviderAndProviderId(oAuthProvider, providerId)
                .orElseGet(() -> usersRepository.save(
                        Users.createUser(providerId, oAuthProvider, email)
                ));

        return new UserDto(
                users.getId(),
                users.getEmail(),
                users.getNickname(),
                users.getRole()
        );
    }
}
