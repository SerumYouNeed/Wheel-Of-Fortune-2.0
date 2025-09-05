package com.serumyouneed.wheel_of_fortune_20.controller;

import com.serumyouneed.wheel_of_fortune_20.model.User;
import com.serumyouneed.wheel_of_fortune_20.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String nickname,
                           @RequestParam String password,
                           Model model) {
        try {
            User newUser = userService.registerUser(nickname, password);
            model.addAttribute("user", newUser);
            // return piece of HTML for htmx
            return "fragments/user :: user-card";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Nickname already used");
            return "fragments/user :: error";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String nickname,
                        @RequestParam String password,
                        Model model) {
        User user = userService.loginUser(nickname, password).orElse(null);
        if (user != null) {
            model.addAttribute("user", user);
            return "fragments/user :: user-card";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "fragments/user :: error";
        }
    }

    @PostMapping("/guest")
    public String guest(@RequestParam String nickname,
                        Model model) {
        User guest = userService.createGuestUser(nickname);
        model.addAttribute("user", guest);
        return "fragments/user :: user-card";
    }
}

