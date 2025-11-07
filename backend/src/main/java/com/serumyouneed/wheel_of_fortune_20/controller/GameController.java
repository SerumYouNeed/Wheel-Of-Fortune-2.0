package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.*;
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
        GameState gameState = gameSessionService.getOrCreateGameState(session);
        gameState.setBigPrize(1000);
        gameSessionService.updateGameState(session, gameState);
        model.addAttribute("user_name", nickname);
        return "fragments/play :: playField";
    }

    @PostMapping("/spin-the-wheel")
    public String spinTheWheel(HttpSession session,
                               Model model,
                               HttpServletResponse response) {
        GameState gameState = gameSessionService.getOrCreateGameState(session);
        Turn turn = gameState.getCurrentTurn();
        if (turn.isWheelSpun() && turn.isLetterPicked()) {
            turn.reset();
        }
        if (!turn.canSpinWheel()) {
            response.setHeader("HX-Retarget", ".message");
            response.setHeader("HX-Reswap", "innerHTML");
            return "fragments/play :: wheelAlreadySpun";
        }

        int field = wheelService.spinTheWheel();
        int prize = wheelService.switchToField(field, 2000);
        gameState.setCurrentPrize(prize);
        turn.setWheelSpun(true);
        gameState.setCurrentTurn(turn);
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
        char guessed = Character.toUpperCase(letter.charAt(0));
        GameState gameState = gameSessionService.getOrCreateGameState(session);
        Turn turn = gameState.getCurrentTurn();
        if (turn.canPickLetter()) {
            if (gameState.ifLetterWasPicked(guessed)) {
                model.addAttribute("letter", guessed);
                response.setHeader("HX-Retarget", ".message");
                response.setHeader("HX-Reswap", "innerHTML");
                return "fragments/play :: alreadyPicked";
            } else {
                gameState.addCharacterToGuessedList(guessed);
                String stateOfThePuzzle = gameState.getMasked();
                String puzzle = gameState.getPuzzle();
                String puzzleAfterGuess = gameService.guessLetter(puzzle, stateOfThePuzzle, guessed);
                gameState.setMasked(puzzleAfterGuess);
                int wonInRound = gameService.foundLetterCounter(puzzle, letter) * gameState.getCurrentPrize();
                List<String> maskedPuzzleAsList = puzzleService.getMaskedPuzzleAsList(puzzleAfterGuess);
                model.addAttribute("masked", maskedPuzzleAsList);
                response.setHeader("HX-Retarget", ".puzzle");
                response.setHeader("HX-Reswap", "innerHTML");
                turn.setLetterPicked(true);
                gameState.setUserMoney(wonInRound);
                model.addAttribute("currentMoney", gameState.getUserMoney());
                gameState.setCurrentTurn(turn);
                gameSessionService.updateGameState(session, gameState);
                return "fragments/play :: puzzleField";
            }
        }
//        tutsj skońcxyłem
        response.setHeader("HX-Retarget", ".message");
        response.setHeader("HX-Reswap", "innerHTML");
        return "fragments/play :: wrongTurn";
    }

    @GetMapping("/know-the-answer")
    public String takeToGuessField(HttpSession session, Model model) {
        GameState gameState = gameSessionService.getOrCreateGameState(session);
        List<String> maskedPuzzleAsList = puzzleService.getMaskedPuzzleAsList(gameState.getMasked());
        model.addAttribute("puzzle", maskedPuzzleAsList);
        return "fragments/guessing :: guessField";
    }

    @PostMapping("/guess-puzzle")
    public String guessPuzzle(@RequestParam("guessedPuzzle") String answer,
                              HttpSession session,
                              Model model,
                              HttpServletResponse response) {
        if (answer == null || answer.isEmpty()) {
            return "fragments/play :: puzzleField";
        }

        GameState gameState = gameSessionService.getOrCreateGameState(session);
        String puzzle = gameState.getPuzzle();
        boolean guess = gameService.guessAnswer(answer, puzzle);

        if (guess) {
            gameState.setUserMoney(gameState.getBigPrize());
            String prize = gameState.getUserMoney();
            model.addAttribute("prize", prize);
            gameSessionService.clearGameState(session);
            return "fragments/guessing :: successGuess";
        }
        return "fragments/guessing :: wrongGuess";
    }

    @GetMapping("/back-to-game")
    public String backToGameAfterWrongAnswer() {
        return "fragments/play :: playField";
    }
}
