package com.buutcamp.controller;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showLoginForm")
    public String showloginPage(Model model) {
        String pwhash = BCrypt.hashpw("test123", BCrypt.gensalt());
        System.out.println(pwhash);
        return "login-page";
    }

    @GetMapping("/logout.done")
    public String logout() {
        return "redirect:/showLoginForm";
    }
}
