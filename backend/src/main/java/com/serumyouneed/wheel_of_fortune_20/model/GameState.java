package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

@Entity
@Table(name = "game_state")
public class GameState {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private Long puzzleId;

    @Column(length = 255)
    private String masked;

    private boolean solved = false;

    // relation to Puzzle entity
    @ManyToOne
    @JoinColumn(name = "puzzle_id", insertable = false, updatable = false)
    private Puzzle puzzle;

    public GameState() {}

    public GameState(Puzzle puzzle, String masked) {
        this.puzzle = puzzle;
        this.puzzleId = puzzle.getId();
        this.masked = masked;
    }

    // getters & setters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPuzzleId() { return puzzleId; }
    public void setPuzzleId(Long puzzleId) { this.puzzleId = puzzleId; }

    public String getMasked() { return masked; }
    public void setMasked(String masked) { this.masked = masked; }

    public boolean isSolved() { return solved; }
    public void setSolved(boolean solved) { this.solved = solved; }

    public Puzzle getPuzzle() { return puzzle; }
}
