package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.User;
import com.sagar.digitaldeals.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "LoginPage";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {

        String pw = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = userService.getByUsernameAndPassword(username, pw);

        if(user != null) {
            session.setAttribute("activeUser", user);
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(300);
            return "redirect:/home";
        }
        model.addAttribute("existMsg", "User does not exist.");
        return "LoginPage";
    }

    @GetMapping("/logout")
    private String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
