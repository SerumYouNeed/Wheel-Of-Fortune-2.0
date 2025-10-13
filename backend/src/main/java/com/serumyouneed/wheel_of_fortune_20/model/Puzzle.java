package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.*;

@Entity
@Table(name = "puzzles")
public class Puzzle{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Constructor required for JPA
    protected Puzzle() {
    }

    // Constructor for the rest of the logic
    public Puzzle(String title) {
        this.title = title;
    }

}
