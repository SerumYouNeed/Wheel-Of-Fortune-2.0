package com.serumyouneed.wheel_of_fortune_20.repository;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameStateRepository extends JpaRepository<GameState, Long> {
    Optional<GameState> findByUserId(Long userId);
}
