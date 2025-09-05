package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registry of a new user.
     */
    public User registerUser(String nickname, String plainPassword) {
        if (userRepository.findByNickname(nickname).isPresent()) {
            throw new IllegalArgumentException("Nickname already in use");
        }

        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        User user = new User(nickname, hashedPassword, false);
        return userRepository.save(user);
    }

    /**
     * Logging of the existing user.
     */
    public Optional<User> loginUser(String nickname, String plainPassword) {
        Optional<User> userOpt = userRepository.findByNickname(nickname);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (BCrypt.checkpw(plainPassword, user.getHashedPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }

    /**
     * Creating a guest player. Guest is not saved in database.
     */
    public User createGuestUser(String nickname) {
        return new User(nickname, true);
    }
}
