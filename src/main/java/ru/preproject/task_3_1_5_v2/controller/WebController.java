package ru.preproject.task_3_1_5_v2.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.preproject.task_3_1_5_v2.model.User;
import ru.preproject.task_3_1_5_v2.service.RoleService;
import ru.preproject.task_3_1_5_v2.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class WebController {

    private final RoleService roleService;
    private final UserService userService;

    public WebController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin(@ModelAttribute("user") User user, Principal principal, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("authenticateduser", userService.findByEmail(principal.getName()));
        model.addAttribute("roles", roleService.findAll());
        return "index";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model) {
        model.addAttribute("authenticateduser", userService.findByEmail(principal.getName()));
        return "index";
    }

}
