package com.serumyouneed.wheel_of_fortune_20.repository;

import com.serumyouneed.wheel_of_fortune_20.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<String, Long> {
    Optional<User> findByNickname(String nickname);
}
