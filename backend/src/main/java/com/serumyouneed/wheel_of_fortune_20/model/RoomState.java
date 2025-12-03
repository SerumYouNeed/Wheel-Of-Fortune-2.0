package com.serumyouneed.wheel_of_fortune_20.model;

import java.util.*;

public class RoomState {
    private final List<PlayerState> playerStates = new ArrayList<>();
    private int currentPlayerIndex = 0;
    private String puzzle;
    private Set<Character> usedLetters = new HashSet<>();

    public List<PlayerState> getPlayerStates() { return playerStates; }
}
