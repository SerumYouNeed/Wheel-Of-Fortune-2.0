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

        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute(GAME_STATE_ATTR, gameState);
        }

        return gameState;
    }

    public void updateGameState(HttpSession session, GameState updatedState) {
        session.setAttribute(GAME_STATE_ATTR, updatedState);
    }

    public void clearGameState(HttpSession session) {
        session.removeAttribute(GAME_STATE_ATTR);
    }
}
