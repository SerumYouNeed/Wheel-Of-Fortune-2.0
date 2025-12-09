package com.serumyouneed.wheel_of_fortune_20.model;

import java.util.*;

public class RoomState {
    private final Map<Long, PlayerState> players = new HashMap<>();
    private String currentPuzzle;
    private int currentPlayerIndex;

    public void addPlayer(User user) {
        players.put(user.getId(), new PlayerState(user.getId()));
    }

    public PlayerState getPlayerState(Long userId) {
        return players.get(userId);
    }
}
