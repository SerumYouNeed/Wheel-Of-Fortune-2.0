package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.GameState;
import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.service.GameSessionService;
import com.serumyouneed.wheel_of_fortune_20.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final GameSessionService gameSessionService;

    public UserController(UserService userService, GameSessionService gameSessionService) {
        this.userService = userService;
        this.gameSessionService = gameSessionService;
    }

    @PostMapping("/api/register")
    public String register(@RequestParam String nickname,
                           @RequestParam String password,
                           HttpSession session,
                           Model model) {
        try {
            User newUser = userService.registerUser(nickname, password);
            gameSessionService.setUserNickname(session, newUser.getNickname());
            model.addAttribute("user_name", newUser.getNickname());
            return "fragments/user :: mode-card";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Nickname already used");
            return "fragments/user :: error";
        }
    }

    @PostMapping("/api/login")
    public String login(@RequestParam String nickname,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.loginUser(nickname, password).orElse(null);
        if (user != null) {
            gameSessionService.setUserNickname(session, user.getNickname());
            model.addAttribute("user_name", user.getNickname());
            return "fragments/user :: mode-card";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "fragments/user :: error";
        }
    }

    @PostMapping("/api/guest")
    public String guest(@RequestParam String nickname,
                        HttpSession session,
                        Model model) {
        User user = userService.createGuestUser(nickname);
        GameState gameState = gameSessionService.getOrCreateGameState(session);
        gameSessionService.setUserNickname(session, user.getNickname());
        model.addAttribute("user_name", user.getNickname());
        return "fragments/user :: mode-card";
    }

}

