package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

@Entity
@Table(name = "puzzles")
public class Puzzle{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String puzzle;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Category category;


    public String getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(String puzzle) {
        this.puzzle = puzzle;
    }

    // Constructor required for JPA
    protected Puzzle() {
    }

    // Constructor for the rest of the logic
    public Puzzle(String puzzle) {
        this.puzzle = puzzle;
    }

    @Override
    public String toString() {
        return "Puzzle: " + puzzle + '\'';
    }

    public Long getId() {
        return id;
    }
}