package com.serumyouneed.wheel_of_fortune_20.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        // return file src/main/resources/templates/index.html
        return "index";
    }
}

