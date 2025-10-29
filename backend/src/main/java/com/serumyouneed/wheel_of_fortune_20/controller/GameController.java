package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.Category;
import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.repository.UserRepository;
import com.serumyouneed.wheel_of_fortune_20.service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameStateService gameStateService;
    private final PuzzleService puzzleService;
    private final GameSessionService gameSessionService;
    private final WheelService wheelService;
    private final GameService gameService;

    public GameController(GameStateService gameStateService,
                          PuzzleService puzzleService,
                          GameSessionService gameSessionService,
                          WheelService wheelService,
                          GameService gameService) {
        this.gameStateService = gameStateService;
        this.puzzleService = puzzleService;
        this.gameSessionService = gameSessionService;
        this.wheelService = wheelService;
        this.gameService = gameService;
    }

    @GetMapping("/single-player-mode")
    public String singlePlayerMode(HttpSession session, Model model) {
        String userNickname = gameSessionService.getUserNicknameAttr(session);
        model.addAttribute("user_name", userNickname);
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
        String nickname = gameSessionService.getUserNicknameAttr(session);
        model.addAttribute("user_name", nickname);
        return "fragments/play :: playField";
    }

    @PostMapping("/spin-the-wheel")
    public String spinTheWheel(HttpSession session, Model model) {
        GameState gameState = gameSessionService.getOrCreateGameState(session);

        int field = wheelService.spinTheWheel();
        int prize = wheelService.switchToField(field, 2000);
        gameState.setCurrentPrize(prize);
        gameSessionService.updateGameState(session, gameState);
        model.addAttribute("prize", prize);
        return "fragments/play :: spinResult";
    }

    @PostMapping("/guess-letter")
    public String guessLetter(@RequestParam("letter") String letter,
                              HttpSession session,
                              Model model,
                              HttpServletResponse response) {
        if (letter == null || letter.isEmpty()) {
            return "fragments/play :: puzzleField";
        }
        char guessed = letter.charAt(0);
        GameState gameState = gameSessionService.getOrCreateGameState(session);

//        if (gameState.ifLetterWasPicked(guessed)) {
//            model.addAttribute("letter", letter);
//            response.setHeader("HX-Retarget", ".message");
//            response.setHeader("HX-Reswap", "innerHTML");
//            return "fragments/play :: alreadyPicked";
//        } else {
            gameState.addCharacterToGuessedList(guessed);
            String puzzleAfterGuess = gameService.guessLetter(gameState, guessed);
            gameState.setMasked(puzzleAfterGuess);
            List<String> maskedPuzzleAsList =  puzzleService.getMaskedPuzzleAsList(puzzleAfterGuess);
            gameSessionService.updateGameState(session, gameState);
            model.addAttribute("masked", maskedPuzzleAsList);

//            response.setHeader("HX-Retarget", ".puzzle");
//            response.setHeader("HX-Reswap", "innerHTML");
            return "fragments/play :: puzzleField";
//        }
    }
}
