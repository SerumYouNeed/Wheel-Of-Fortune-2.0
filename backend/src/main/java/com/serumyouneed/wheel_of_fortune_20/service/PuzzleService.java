package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.repository.PuzzleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PuzzleService {

    private final PuzzleRepository puzzleRepository;

    public PuzzleService(PuzzleRepository puzzleRepository) {
        this.puzzleRepository = puzzleRepository;
    }

    /**
     * Fetch random puzzle from database.
     */
    public Puzzle getPuzzle() {
        Random random = new Random();
        long count = puzzleRepository.count();
        long randomId = 1 + new Random().nextLong(count);
        return puzzleRepository.findByID(randomId).orElseThrow();
    }

    public List<String> getMaskedPuzzleAsList(Puzzle p) {
        String puzzle = p.getPuzzle();
        return puzzle.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
    }

    /**
     * Function masking an input string
     * @param puzzle (String)
     * @return masked (String): Letters covered by '_'. Spaces stays.
     */
    public String maskingPuzzle(String puzzle) {
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < puzzle.length(); i++) {
            if (puzzle.charAt(i) == ' ') {
                masked.append(' ');
            } else if (puzzle.charAt(i) == '\''){
                masked.append('\'');
            } else if (puzzle.charAt(i) == '-') {
                masked.append('-');
            } else {
                masked.append('_');
            }
        }
        return masked.toString();
    }


    /**
     * Function uncover masked field if player input is in puzzle.
     * @param puzzle       (String): Actual puzzle from database, set before round.
     * @param input        (String): Player's guessed letter.
     * @return updated     (String): Modified masked puzzle after letter checking.
     */
    public String newDisplay (String maskedPuzzle, String puzzle, String input) {
        input = input.toUpperCase();
        StringBuilder updated = new StringBuilder(maskedPuzzle);
        boolean found = false;

        for (int i = 0; i < puzzle.length(); i++) {
            if (puzzle.charAt(i) == input.charAt(0)) {
                updated.setCharAt(i, input.charAt(0));
                found = true;
            }
        }
        if (!found) {
            return "";
        }
        return updated.toString();
    }

}
