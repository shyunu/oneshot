package com.project.oneshot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/include")
public class MainController {

    @GetMapping("/login")
    public String login() {
        return "include/login";
    }

    @GetMapping("/mainHome")
    public String mainHome() {
        return "include/mainHome";
    }
}
