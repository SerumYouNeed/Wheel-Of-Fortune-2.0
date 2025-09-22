package com.serumyouneed.wheel_of_fortune_20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

@GetMapping("/guest")
    public String guest() {
        return "fragments/guest :: guestFragment";
    }

    @GetMapping("/register")
    public String register() {
        return "fragments/register :: registerFragment";
    }

    @GetMapping("/login")
    public String login() {
        return "fragments/login :: loginFragment";
    }
}
