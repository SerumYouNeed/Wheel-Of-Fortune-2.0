package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.repository.PuzzleRepository;

import java.util.Random;

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
        return puzzleRepository.findById(randomId).orElseThrow();
    }
}
