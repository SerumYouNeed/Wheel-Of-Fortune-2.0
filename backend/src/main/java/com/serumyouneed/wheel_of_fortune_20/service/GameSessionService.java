package com.serumyouneed.wheel_of_fortune_20.service;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class GameSessionService {
    private static final String GAME_STATE_ATTR = "gameState";
    private static final String USER_NICKNAME_ATTR = "userNickname";



    public GameState getOrCreateGameState(HttpSession session) {
        GameState gameState = (GameState) session.getAttribute(GAME_STATE_ATTR);

        if (gameState == null) {
            gameState = new GameState();
            session.setAttribute(GAME_STATE_ATTR, gameState);
        }

        return gameState;
    }

    public String getUserNicknameAttr(HttpSession session) {
        return (String) session.getAttribute(USER_NICKNAME_ATTR);
    }

    public void setUserNickname(HttpSession session, String nickname) {
        session.setAttribute(USER_NICKNAME_ATTR, nickname);
    }

    public void updateGameState(HttpSession session, GameState updatedState) {
        session.setAttribute(GAME_STATE_ATTR, updatedState);
    }

    public void clearGameState(HttpSession session) {
        session.removeAttribute(GAME_STATE_ATTR);
    }

    public boolean hasActiveGame(HttpSession session) {
        return session.getAttribute(GAME_STATE_ATTR) != null;
    }
}
