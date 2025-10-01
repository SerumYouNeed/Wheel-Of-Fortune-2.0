//package com.serumyouneed.wheel_of_fortune_20.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class RegisterController {
//
//    @PostMapping("/api/register")
//    @ResponseBody // <- zwraca zwykły tekst, nie szablon
//    public String register(@RequestParam String username,
//                           @RequestParam String password) {
//        return "Dostałem: username=" + username + ", password=" + password;
//    }
//
//    @PostMapping("/api/login")
//    @ResponseBody // <- zwraca zwykły tekst, nie szablon
//    public String login(@RequestParam String username,
//                           @RequestParam String password) {
//        return "Dostałem: username=" + username + ", password=" + password;
//    }
//
//    @PostMapping("/api/asGuest")
//    @ResponseBody // <- zwraca zwykły tekst, nie szablon
//    public String assGuest(@RequestParam String username) {
//        return "Dostałem: username=" + username;
//    }
//}
