package com.serumyouneed.wheel_of_fortune_20.model;

public class PlayerState {
    private final String playerId;
    private int prizeMoney;
    private boolean turn;
    private char lastSpinLetter;

    public PlayerState(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

}