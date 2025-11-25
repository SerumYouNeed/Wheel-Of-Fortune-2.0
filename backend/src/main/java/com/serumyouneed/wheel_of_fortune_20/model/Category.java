package com.serumyouneed.wheel_of_fortune_20.model;

public enum Category {

    MOVIE("Movies"),
    PROVERB("Proverbs");

    private final String label;

    Category(String label) { this.label = label; }

    public String getLabel() {
        return label;
    }
}