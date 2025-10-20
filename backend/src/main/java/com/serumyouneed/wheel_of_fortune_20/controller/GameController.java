package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.Category;
import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.Puzzle;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.repository.UserRepository;
import com.serumyouneed.wheel_of_fortune_20.service.GameStateService;
import com.serumyouneed.wheel_of_fortune_20.service.PuzzleService;
import com.serumyouneed.wheel_of_fortune_20.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameStateService gameStateService;
    private final PuzzleService puzzleService;

    public GameController(GameStateService gameStateService, PuzzleService puzzleService) {
        this.gameStateService = gameStateService;
        this.puzzleService = puzzleService;
    }

    @GetMapping("/single-player-mode")
    public String singlePlayerMode(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
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
    public String startNewGame(@ModelAttribute("selectedCategory") Category category, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        GameState game = gameStateService.createNewGame(user, puzzleService.getPuzzle(category));
        model.addAttribute("puzzle", puzzleService.getMaskedPuzzleAsList(game.getMasked()));
        model.addAttribute("user", user);
        return "fragments/play :: playField";
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
