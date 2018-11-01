package com.buutcamp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontPageController {

    @GetMapping("/")
    public String showHome() {
        return "homepage";
    }

    @GetMapping("/manager")
    public String showManager() {
        return "manager-page";
    }

    @GetMapping("/forbidden")
    public String forbidden(){
        return "forbidden";
    }
}
