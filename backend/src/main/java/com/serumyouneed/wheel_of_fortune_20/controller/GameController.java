package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.service.GameStateService;
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

    @GetMapping("/start")
    public String startGame(Model model) {
        GameState game = gameStateService.startNewGame(1L); // tymczasowy userId = 1
        model.addAttribute("puzzle", game.getMasked().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .toList());
        return "fragments/selectors :: puzzleSelected";
    }

    @PostMapping("/guess")
    public String guess(@RequestParam("letter") char letter, Model model) {
        GameState game = gameStateService.guessLetter(1L, letter);
        model.addAttribute("puzzle", game.getMasked().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .toList());
        return "fragments/selectors :: puzzleSelected";
    }

    @GetMapping("/play")
    public String play(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "fragments/play :: playField";
    }

}
