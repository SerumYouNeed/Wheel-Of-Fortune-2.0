package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    /**
     * Function unwield masked field if player input is in puzzle.
     * @param puzzle       (GameState): Actual puzzle.
     * @param statePuzzle  (GameState): Actual state of the masked puzzle.
     * @param letter       (char): Player's guessed letter.
     * @return updated     (String): Modified masked puzzle after letter checking.
     */
    public String guessLetter (String puzzle, String statePuzzle, char letter) {
        StringBuilder masked = new StringBuilder(statePuzzle);;
        boolean found = false;

        for (int i = 0; i < puzzle.length(); i++) {
            if (puzzle.charAt(i) == letter) {
                masked.setCharAt(i, letter);
                found = true;
            }
        }
        if (!found) {
            return statePuzzle;
        }
        return masked.toString();
    }

    public int foundLetterCounter (String puzzle, String input) {
        int counter = 0;
        String guessedLetter = input.toUpperCase();
        for (int i = 0; i < puzzle.length(); i++) {
            if (puzzle.charAt(i) == guessedLetter.charAt(0)) {
                counter += 1;
            }
        }
        return counter;
    }

    public boolean guessAnswer(String input, String puzzle) {
        return input.toUpperCase().equals(puzzle);
    }

}
