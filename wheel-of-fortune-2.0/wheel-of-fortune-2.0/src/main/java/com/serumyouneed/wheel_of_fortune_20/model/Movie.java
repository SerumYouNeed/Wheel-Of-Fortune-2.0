package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Supporting class to create Movie object. Title field will be used as a puzzle string to mask during the game.
 *
 */
@Entity
@Table(name = "mo"vie)
public class Movie implements Guessable{
    private final int id;
    private final String title;
    private final int releaseYear;

    public Movie(int id, String title, int releaseYear) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getText() {
        return title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    // toString for debugging purpose only
    @Override
    public String toString() {
        return title + " (" + releaseYear + ")";
    }
}
