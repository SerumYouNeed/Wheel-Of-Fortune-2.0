package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game_state")
public class GameState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String puzzle;

    @Column(length = 255)
    private String masked;

    private boolean solved = false;
    private int currentPrize;
    private Turn currentTurn = new Turn();
    private Integer userMoney;

    private final List<Character> guessedLetters = new ArrayList<>();

    private LocalDateTime lastUpdated = LocalDateTime.now();

    public GameState() {}

    // getters & setters
    public String getUserMoney() { return userMoney.toString(); }

    public Turn getCurrentTurn() { return currentTurn; }
    public void setCurrentTurn(Turn currentTurn) { this.currentTurn = currentTurn; }

    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }

    public int getCurrentPrize() { return currentPrize; }
    public void setCurrentPrize(int currentPrize) { this.currentPrize = currentPrize; }

    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getPuzzle() { return puzzle; }
    public void setPuzzle(String puzzle) { this.puzzle = puzzle; }

    public String getMasked() { return masked; }
    public void setMasked(String masked) { this.masked = masked; }

    public boolean isSolved() { return solved; }
    public void setSolved(boolean solved) { this.solved = solved; }

    public void addCharacterToGuessedList (char letter) {
        guessedLetters.add(letter);
    }

    public boolean ifLetterWasPicked(char letter) { return guessedLetters.contains(letter); }
}
