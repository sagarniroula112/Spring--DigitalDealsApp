package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.Cart;
import com.sagar.digitaldeals.model.User;
import com.sagar.digitaldeals.service.CartService;
import com.sagar.digitaldeals.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @GetMapping("/signup")
    public String signup(){
        return "RegistrationPage";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute User user, Model model){
        // Save the User first
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        User savedUser = userService.addUser(user);

        // Create and associate a Cart with the User
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartService.addCart(cart);

        // Associate the Cart back to the User and save the User again
        savedUser.setCart(cart);
        userService.updateUser(savedUser);

        model.addAttribute("existMsg", "User registration successful!");
        return "LoginPage";
    }
}
