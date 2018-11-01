package com.buutcamp.controller;

import com.buutcamp.controller.user.NewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationController {

    @Autowired
    private UserDetailsManager userDetailManager;

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {

        model.addAttribute("newuser", new NewUser());

        List<String> roles = new ArrayList<String>();

        roles.add("visitor");
        roles.add("manager");

        model.addAttribute("roles", roles);

        return "registration-form";
    }

    @PostMapping("/processRegistration")
    public String processRegistration (
            @Valid @ModelAttribute("newuser") NewUser user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        //Test if input is correct
        if (bindingResult.hasErrors()) {

            redirectAttributes.addFlashAttribute("registrationError", "Username or password is too short");
            return "redirect:/register";
        }
        //test if user already exists
        String username = user.getUserName();

        if (userDetailManager.userExists(username)) {

            redirectAttributes.addFlashAttribute("registrationError", "User already exists");
            return "redirect:/register";
        }

        //validation passed -> add user to database

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedpassword = "{bcrypt}" + passwordEncoder.encode(user.getPassword());

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList();

        authorities.add(new SimpleGrantedAuthority("ROLE_visitor"));

        if (!user.getRole().equals("visitor")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        }

        User tempUser = new User(username,encodedpassword,authorities);

        userDetailManager.createUser(tempUser);

        //return to loginPage
        redirectAttributes.addFlashAttribute(
                "registrationComplete", "Registration successful");

        return "redirect:/showLoginForm";
    }

}
