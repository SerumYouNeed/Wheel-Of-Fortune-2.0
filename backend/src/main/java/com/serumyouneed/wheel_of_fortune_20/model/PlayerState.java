package com.serumyouneed.wheel_of_fortune_20.model;

public class PlayerState {
    private final Long playerId;
    private int prizeMoney;
    private boolean turn;
    private char lastSpinLetter;

    public PlayerState(Long playerId) {
        this.playerId = playerId;
    }

    public Long getPlayerId() {
        return playerId;
    }

}