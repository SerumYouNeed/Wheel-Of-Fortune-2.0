package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.repository.GameStateRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameStateService {

    private final GameStateRepository repo;
    private final PuzzleService puzzleService;

    public GameStateService(GameStateRepository repo, PuzzleService puzzleService) {
        this.repo = repo;
        this.puzzleService = puzzleService;
    }

    public GameState loadPreviousGame(User user) {
        return repo.findTopByUserOrderByLastUpdatedDesc(user)
                .orElseThrow(() -> new IllegalStateException("No game found for user"));
    }
    public GameState createNewGame(User user, Puzzle puzzle, String masked) {
        GameState newGame = new GameState();
        newGame.setUser(user);
        newGame.setPuzzle(puzzle);
        newGame.setMasked(masked);
        newGame.setSolved(false);
        return repo.save(newGame);
    }

    public GameState guessLetter(User user, char letter) {
        GameState state = repo.findByUserAndSolvedIsFalse(user)
                .orElseThrow(() -> new IllegalStateException("No game found for user"));

        String puzzleText = state.getPuzzle().getPuzzle();
        StringBuilder sb = new StringBuilder(state.getMasked());

        for (int i = 0; i < puzzleText.length(); i++) {
            if (Character.toLowerCase(puzzleText.charAt(i)) == Character.toLowerCase(letter)) {
                sb.setCharAt(i, puzzleText.charAt(i));
            }
        }

        state.setMasked(sb.toString());
        if (!sb.toString().contains("_")) {
            state.setSolved(true);
        }

        return repo.save(state);
    }

    public GameState getGame(User user) {
        return repo.findTopByUserOrderByIdDesc(user).orElse(null);
    }
}