package com.serumyouneed.wheel_of_fortune_20.model;

public class Turn {
    private boolean spunWheel = false;
    private boolean letterPicked = false;

    public boolean canSpinWheel() {
        return !spunWheel;
    }

    public boolean canPickLetter() {
        return spunWheel && !letterPicked;
    }

    public void setSpunWheel(boolean spunWheel) {
        this.spunWheel = spunWheel;
    }

    public void setLetterPicked(boolean letterPicked) {
        this.letterPicked = letterPicked;
    }

    public void reset() {
        spunWheel = false;
        letterPicked = false;
    }
}
