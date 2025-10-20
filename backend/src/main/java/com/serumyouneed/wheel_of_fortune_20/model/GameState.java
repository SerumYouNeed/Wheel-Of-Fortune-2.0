package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_state")
public class GameState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    @Column(length = 255)
    private String masked;

    private boolean solved = false;

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    private LocalDateTime lastUpdated = LocalDateTime.now();

    public GameState() {}

    public GameState(User user, Puzzle puzzle, String masked) {
        this.user = user;
        this.puzzle = puzzle;
        this.masked = masked;
    }

    // getters & setters
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Puzzle getPuzzle() { return puzzle; }
    public void setPuzzle(Puzzle puzzle) { this.puzzle = puzzle; }

    public String getMasked() { return masked; }
    public void setMasked(String masked) { this.masked = masked; }

    public boolean isSolved() { return solved; }
    public void setSolved(boolean solved) { this.solved = solved; }
}
