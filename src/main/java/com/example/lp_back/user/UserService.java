package com.example.lp_back.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.lp_back.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserEntity create(final UserEntity user) {
        if (user == null || user.getId() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        final String id = user.getId();
        if(userRepository.existsById(id)) {
            throw new RuntimeException("ID already exists");
        }

        return userRepository.save(user);
    }

    public UserEntity getByCredentials(final String id, final String password, final PasswordEncoder encoder) {
        final UserEntity originalUser = userRepository.findById(id);

        if (originalUser != null && encoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }

        return null;
    }

    public UserEntity getUser(Long usernum) {
        Optional<UserEntity> user = this.userRepository.findById(usernum);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("User not found");
        }
    }
}