package com.serumyouneed.wheel_of_fortune_20.repository;

import com.serumyouneed.wheel_of_fortune_20.model.Category;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PuzzleRepository extends JpaRepository<Puzzle, Long> {
    Optional<Puzzle> findById(Long id);
    List<Puzzle> findByCategory(Category category);
}
