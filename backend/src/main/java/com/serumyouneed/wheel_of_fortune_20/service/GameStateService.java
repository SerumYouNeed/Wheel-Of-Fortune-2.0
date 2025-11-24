package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.repository.GameStateRepository;
import com.serumyouneed.wheel_of_fortune_20.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GameStateService {

    private final GameStateRepository repo;

    public GameStateService(GameStateRepository repo, PuzzleService puzzleService, UserRepository userRepo) {
        this.repo = repo;
    }

    public GameState loadPreviousGame(User user) {
        return repo.findTopByUserOrderByLastUpdatedDesc(user)
                .orElseThrow(() -> new IllegalStateException("No game found for user"));
    }

    public void saveCurrentGameState(GameState state) {
        repo.save(state);
    }

    public GameState getLastGame(User user) {
        return repo.findTopByUserOrderByIdDesc(user).orElse(null);
    }
}