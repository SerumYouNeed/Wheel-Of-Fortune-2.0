package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.repository.UserRepository;
import com.serumyouneed.wheel_of_fortune_20.service.GameStateService;
import com.serumyouneed.wheel_of_fortune_20.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/game")
public class GameController {

    private final GameStateService gameStateService;

    public GameController(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    @GetMapping("/single-player-mode")
    public String singlePlayerMode() {
        return "fragments/user :: starting-singleplayer-card";
    }

    @GetMapping("/load-previous-game")
    public String loadPreviousGame(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        GameState state = gameStateService.loadPreviousGame(user);
        model.addAttribute("game", state);
    }

    @GetMapping("/start-new-game")
    public String startGame(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        GameState game = gameStateService.startNewGame(user);
        model.addAttribute("puzzle", game.getMasked().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .toList());
        return "fragments/selectors :: puzzleSelected";
    }

//    @PostMapping("/guess")
//    public String guess(@RequestParam("letter") char letter, Model model) {
//        GameState game = gameStateService.guessLetter(1L, letter);
//        model.addAttribute("puzzle", game.getMasked().chars()
//                .mapToObj(c -> String.valueOf((char) c))
//                .toList());
//        return "fragments/selectors :: puzzleSelected";
//    }

    @GetMapping("/play")
    public String play(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "fragments/play :: playField";
    }

}
