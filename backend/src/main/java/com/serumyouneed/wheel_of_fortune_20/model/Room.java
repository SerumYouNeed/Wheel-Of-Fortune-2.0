package com.serumyouneed.wheel_of_fortune_20.model;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private final UUID RoomId = UUID.randomUUID();
    private final List<User> players;
    private final RoomState state;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public Room(List<User> players) {
        this.players = players;
        this.state = new RoomState();
        for (User u : players) {
            this.state.getPlayerStates().add(new PlayerState(u.getId().toString()));
        }
    }
// do poprawy
    public void addEmitter(SseEmitter emitter) {
        emitters.add(emitter);
    }

    public void broadcast(String data) {
        emitters.forEach(e -> {
            try { e.send(data); }
            catch (Exception ex) { emitters.remove(e); }
        });
    }

    public List<User> getPlayers() {
        return players;
    }
}

