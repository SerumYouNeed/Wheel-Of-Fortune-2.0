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
        User user = (User) session.getAttribute("user");
        GameState state = gameSessionService.getOrCreateGameState(session);
        model.addAttribute("user", user);
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
        User user = (User) session.getAttribute("user");
        Category category = (Category) session.getAttribute("category");
        GameState game = gameStateService.createNewGame(user, puzzleService.getPuzzle(category));
        model.addAttribute("puzzle", puzzleService.getMaskedPuzzleAsList(game.getMasked()));
        model.addAttribute("user", user);
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
//    @PostMapping("/guess")
//    public String guess(@RequestParam("letter") char letter, Model model) {
//        GameState game = gameStateService.guessLetter(1L, letter);
//        model.addAttribute("puzzle", game.getMasked().chars()
//                .mapToObj(c -> String.valueOf((char) c))
//                .toList());
//        return "fragments/selectors :: puzzleSelected";
//    }
}
