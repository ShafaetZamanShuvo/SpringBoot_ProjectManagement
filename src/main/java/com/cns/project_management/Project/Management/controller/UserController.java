package com.cns.project_management.Project.Management.controller;

import com.cns.project_management.Project.Management.controller.DTO.UserRegistrationDTO;
import com.cns.project_management.Project.Management.entity.User;
import com.cns.project_management.Project.Management.repository.UserRepository;
import com.cns.project_management.Project.Management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("user") User user, Model model, HttpSession session) {
        //check if user exists
        User userExists = userRepository.findByUsername(user.getUsername());
        if (userExists == null) {
            model.addAttribute("loginError", true);
            return "login";
        }
        //check if password is correct
        if (!userExists.getPassword().equals(user.getPassword())) {
            model.addAttribute("loginError", true);
            return "login";
        }
        //login successful
        session.setAttribute("loggedInUser", userExists);
        return "redirect:/projects";
    }

    //user register
    @GetMapping("/register")
    public String registrationForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/register")
    public String registerPost(@ModelAttribute("user") User user, Model model) {
        //check if user already exists
        User userExists = userRepository.findByUsername(user.getUsername());
        if (userExists != null) {
            model.addAttribute("exists", true);
            return "register";
        }
        //save user to database
        userRepository.save(user);
        return "redirect:/users/login";
    }



}
