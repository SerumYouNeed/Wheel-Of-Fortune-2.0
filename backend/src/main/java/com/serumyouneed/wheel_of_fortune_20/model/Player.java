package com.serumyouneed.wheel_of_fortune_20.model;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

public class Player {


    private final String sessionId;
    private final User user;
    private final SseEmitter emitter;
    private Long roomId;
    private boolean ready;
    private boolean turn;

    public Player(User user) {
        this.user = user;
        this.sessionId = UUID.randomUUID().toString();
        this.emitter = new SseEmitter(0L);
    }

    public String getSessionId() { return sessionId; }
    public SseEmitter getEmitter() { return emitter; }
    public Long getRoomId() { return roomId; }
    public void setRoomId(Long roomId) { this.roomId = roomId; }
}

