package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.repository.GameStateRepository;
import org.springframework.stereotype.Service;

@Service
public class GameStateService {

    private final GameStateRepository repo;
    private final PuzzleService puzzleService;

    public GameStateService(GameStateRepository repo, PuzzleService puzzleService) {
        this.repo = repo;
        this.puzzleService = puzzleService;
    }

    public GameState startNewGame(Long userId) {
        Puzzle puzzle = puzzleService.getPuzzle();
        String masked = puzzleService.maskingPuzzle(puzzle);

        GameState state = new GameState(puzzle, masked);
        state.setUserId(userId);
        return repo.save(state);
    }

    public GameState guessLetter(Long userId, char letter) {
        GameState state = repo.findByUserId(userId)
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

    public GameState getGame(Long userId) {
        return repo.findByUserId(userId).orElse(null);
    }
}