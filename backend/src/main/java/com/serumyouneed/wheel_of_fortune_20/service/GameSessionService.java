package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService {
    private static final String GAME_STATE_ATTR = "gameState";

    public GameState getOrCreateGameState(HttpSession session) {
        GameState gameState = (GameState) session.getAttribute(GAME_STATE_ATTR);
        User user = (User) session.getAttribute("user");

        if (gameState == null) {
            gameState = new GameState();
            gameState.setUser(user);
            session.setAttribute(GAME_STATE_ATTR, gameState);
        }

        return gameState;
    }// refaktoryzacja

    public void updateGameState(HttpSession session, GameState updatedState) {
        session.setAttribute(GAME_STATE_ATTR, updatedState);
    }

    public void clearGameState(HttpSession session) {
        session.removeAttribute(GAME_STATE_ATTR);
    }
}
