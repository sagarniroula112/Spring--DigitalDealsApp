package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    private String index(HttpSession session) {
        User user = (User) session.getAttribute("activeUser");

        if(user != null) {
            if(user.getRole().equals("admin")) {
                return "AdminDashboard";
            } else {
                return "index";
            }
        }
        return "index";
    }

    @GetMapping("/home")
    private String home(HttpSession session) {
        User user = (User) session.getAttribute("activeUser");

        if(user != null) {
            if(user.getRole().equals("admin")) {
                return "AdminDashboard";
            } else {
                return "index";
            }
        }
        return "index";
    }
}
