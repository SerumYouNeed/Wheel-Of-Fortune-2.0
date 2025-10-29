package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    /**
     * Function unwield masked field if player input is in puzzle.
     * @param gameState    (GameState): Actual state of the game before round.
     * @param letter       (char): Player's guessed letter.
     * @return updated     (String): Modified masked puzzle after letter checking.
     */
    public String guessLetter (GameState gameState, char letter) {
        if (!gameState.ifLetterWasPicked(letter)) {

            boolean found = false;

            Puzzle puzzle = gameState.getPuzzle();
            String puzzleString = puzzle.getPuzzle();
            StringBuilder masked = new StringBuilder(gameState.getMasked());

            for (int i = 0; i < puzzleString.length(); i++) {
                if (puzzleString.charAt(i) == letter) {
                    masked.setCharAt(i, letter);
                    found = true;
                }
            }
            if (!found) {
                return gameState.getMasked();
            }
            return masked.toString();
        } else {
            return gameState.getMasked();
        }
    }

    private int foundLetterCounter (String proverb, String input) {
        int counter = 0;
        for (int i = 0; i < proverb.length(); i++) {
            if (proverb.charAt(i) == input.charAt(0)) {
                counter += 1;
            }
        }
        return counter;
    }

//    private boolean guessAnswer(String input, String proverb) {
//        if (input.toUpperCase().equals(proverb)) {
//            Printer.print(Messages.CORRECT);
//            return true;
//        } else {
//            Printer.print(Messages.WRONG);
//            return false;
//        }
//    }

}
