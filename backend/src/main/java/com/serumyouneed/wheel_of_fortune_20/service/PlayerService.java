package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.Player;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerService {
    @Service
    public static class PlayerRegistry {

        private final Map<String, Player> players = new ConcurrentHashMap<>();

        public Player register(User user) {
            Player p = new Player(user);
            players.put(p.getSessionId(), p);
            return p;
        }

        public Player get(String sessionId) {
            return players.get(sessionId);
        }
    }
}
