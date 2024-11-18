package com.team4.lp_back.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.team4.lp_back.exception.DataNotFoundException;
import org.springframework.stereotype.Service;
import com.team4.lp_back.entity.UserEntity;
import com.team4.lp_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserEntity create(final UserEntity user) {
        if (user == null || user.getUserName() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String userName = user.getUserName();
        if(userRepository.existsByUserName(userName)) {
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(user);
    }

    public UserEntity getByCredentials(final String userName, final String password, final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findByUserName(userName);

        if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }

        return null;
    }

    public UserEntity getUser(Long userId) {
        Optional<UserEntity> user = this.userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("User not found");
        }
    }
}
