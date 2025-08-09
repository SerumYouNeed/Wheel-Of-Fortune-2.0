package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

/**
 * Class for logging purpose. Help to create player profile in database.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final boolean isGuest;
    private final String nickname;
    private String hashedPassword;

    public User (String nickname, String hashedPassword, boolean isGuest) {
        this.nickname = nickname;
        this.isGuest = isGuest;
        this.hashedPassword = hashedPassword;
    }
    public User (String nickname, boolean isGuest) {
        this.nickname = nickname;
        this.isGuest = isGuest;
    }

    // Getters
    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isGuest() {
        return isGuest;
    }
}
