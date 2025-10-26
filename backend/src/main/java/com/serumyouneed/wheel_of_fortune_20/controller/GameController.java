package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.Category;
import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.repository.UserRepository;
import com.serumyouneed.wheel_of_fortune_20.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameStateService gameStateService;
    private final PuzzleService puzzleService;
    private final GameSessionService gameSessionService;
    private final WheelService wheelService;

    public GameController(GameStateService gameStateService,
                          PuzzleService puzzleService,
                          GameSessionService gameSessionService,
                          WheelService wheelService) {
        this.gameStateService = gameStateService;
        this.puzzleService = puzzleService;
        this.gameSessionService = gameSessionService;
        this.wheelService = wheelService;
    }

    @GetMapping("/single-player-mode")
    public String singlePlayerMode(HttpSession session, Model model) {
        String user_name = (String) session.getAttribute("user_name");
        GameState state = gameSessionService.getOrCreateGameState(session);
        model.addAttribute("user", user_name);
        model.addAttribute("state", state);
        return "fragments/user :: starting-singleplayer-card";
    }

    @GetMapping("/load-previous-game")
    public String loadPreviousGame(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        GameState state = gameStateService.loadPreviousGame(user);
        model.addAttribute("game", state);
        model.addAttribute("user", user);
        return "fragments/play :: playField";
    }

    @GetMapping("/start-new-game")
    public String startNewGame(HttpSession session, Model model) {
        Category category = (Category) session.getAttribute("category");
        GameState game = gameStateService.createNewGame(puzzleService.getPuzzle(category));
        Long game_id = game.getId();
        session.setAttribute("game_id", game_id);
        String user_name = (String) session.getAttribute(user_name)
        model.addAttribute("user_name", session.getAttribute(user_name));
        model.addAttribute("puzzle", puzzleService.getMaskedPuzzleAsList(game.getMasked()));
        return "fragments/play :: playField";
    }

    @PostMapping("/spin-the-wheel")
    public String spinTheWheel(HttpSession session, Model model) {
        GameState gameState = (GameState) session.getAttribute("gameState");

        if (gameState == null) {
            return "fragments/error :: noGameActive";
        }

        int field = wheelService.spinTheWheel();
        int prize = wheelService.switchToField(field, 2000);
        gameState.setCurrentPrize(prize);
        session.setAttribute("gameState", gameState);
        session.setAttribute("prize", prize);
        model.addAttribute("prize", prize);
        return "fragments/play :: spinResult";
    }
}
