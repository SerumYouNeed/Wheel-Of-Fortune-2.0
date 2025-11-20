package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.Category;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.repository.PuzzleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    public Puzzle getPuzzle(Category category) {

        List<Puzzle> puzzles = puzzleRepository.findByCategory(category);
        if (puzzles.isEmpty()) {
            throw new IllegalStateException("No puzzles in category!");
        }

        int randomIndex = new Random().nextInt(puzzles.size());
        return puzzles.get(randomIndex);
    }

    public List<String> getMaskedPuzzleAsList(String puzzle) {
        return puzzle.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .collect(Collectors.toList());
    }

    public List<String> splitIntoWords(String puzzle) {
        return Arrays.asList(puzzle.split(" "));
    }

    public List<List<String>> wordsAsLetters(String puzzle) {
        return Arrays.stream(puzzle.split(" "))
                .map(word -> word.chars()
                        .mapToObj(c -> String.valueOf((char) c))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public int maxWordLength(List<List<String>> listOfWords) {
        return listOfWords.stream()
                .mapToInt(List::size)
                .max()
                .orElse(1);
    }

    /**
     * Function masking an input string
     * @param puzzle (Puzzle)
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

}
