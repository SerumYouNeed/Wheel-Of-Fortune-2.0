package com.serumyouneed.wheel_of_fortune_20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @GetMapping("/select-category")
    public String drawCategory() {

    }

    @GetMapping("/select-puzzle")
    public String drawPuzzle() {

    }

    @GetMapping("/play")
    public String play() {
        return "fragments/play :: playField";
    }

}
