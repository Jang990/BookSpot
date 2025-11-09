package com.bookspot.users.application;

import com.bookspot.users.domain.OAuthProvider;
import com.bookspot.users.domain.Users;
import com.bookspot.users.domain.UsersRepository;
import com.bookspot.users.domain.exception.UserNotFoundException;
import com.bookspot.users.presentation.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;

    protected UserDto createOrFindUser(String email, OAuthProvider oAuthProvider, String providerId) {
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

    public void deleteUser(long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        users.delete();
        usersRepository.deleteById(userId);
    }

    public UserDetailResponse findMyInfo(long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return new UserDetailResponse(
                users.getEmail(),
                users.getProvider().toString(),
                users.getCreatedAt()
        );
    }
}
