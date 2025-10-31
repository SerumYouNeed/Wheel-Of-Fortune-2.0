package com.serumyouneed.wheel_of_fortune_20.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Turn {
    private boolean wheelSpun;
    private boolean letterPicked;

    public Turn() {
        this.wheelSpun = false;
        this.letterPicked = false;
    }

    public boolean isWheelSpun() {
        return wheelSpun;
    }

    public void setWheelSpun(boolean wheelSpun) {
        this.wheelSpun = wheelSpun;
    }

    public boolean isLetterPicked() {
        return letterPicked;
    }

    public void setLetterPicked(boolean letterPicked) {
        this.letterPicked = letterPicked;
    }

    public boolean canSpinWheel() {
        return !wheelSpun;
    }

    public boolean canPickLetter() {
        return wheelSpun && !letterPicked;
    }

    public void reset() {
        this.wheelSpun = false;
        this.letterPicked = false;
    }
}
