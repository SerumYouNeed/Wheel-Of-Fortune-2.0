package com.serumyouneed.wheel_of_fortune_20.model;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private final UUID roomId = UUID.randomUUID();
    private final List<User> players;
    private final RoomState state = new RoomState();
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public Room(List<User> players) {
        this.players = players;
        players.forEach(state::addPlayer);
    }

    public RoomState getState() {
        return state;
    }
}

