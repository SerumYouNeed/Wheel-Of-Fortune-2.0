package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

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
}
