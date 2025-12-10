package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class for logging purpose. Represents a player profile in the database.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String nickname;

    @Column(name = "password_hash")
    private String hashedPassword;

    @Column(nullable = false)
    private boolean guest;

    @Transient
    private String runtimeId = UUID.randomUUID().toString();

    // Relation to GameState entity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GameState> games = new ArrayList<>();

    // Constructor required for JPA
    protected User() {
    }

    // Constructor for the rest of the logic
    public User(String nickname, String hashedPassword, boolean guest) {
        this.nickname = nickname;
        this.hashedPassword = hashedPassword;
        this.guest = guest;
    }

    public User(String nickname, boolean guest) {
        this.nickname = nickname;
        this.guest = guest;
    }

    // Getters and setters mostly for JPA
    public Long getId() {
        return id;
    }

    public String getRuntimeId() { return runtimeId; }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean isGuest() {
        return guest;
    }

    public void setGuest(boolean guest) {
        this.guest = guest;
    }

    public List<GameState> getGames() { return games; }
    public void addGame(GameState gameState) {
        games.add(gameState);
        gameState.setUser(this);
    }
}
