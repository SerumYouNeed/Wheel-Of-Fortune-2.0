package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

/**
 * Supporting class to create Proverb object. Proverb field will be used as a puzzle string to mask during the game.
 */
@Entity
@Table(name = "proverb")
public class Proverb implements Guessable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final int id;
    private final String proverb;
    private final String meaning;

    public Proverb(int id, String proverb, String meaning) {
        this.id = id;
        this.proverb = proverb;
        this.meaning = meaning;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getText() {
        return proverb;
    }

    public String getMeaning() {
        return meaning;
    }

    // toString for debugging purpose
    @Override
    public String toString() {
        return proverb + ": " + meaning;
    }
}
