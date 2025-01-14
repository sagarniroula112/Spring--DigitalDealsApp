package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.Order;
import com.sagar.digitaldeals.model.User;
import com.sagar.digitaldeals.repository.OrderRepository;
import com.sagar.digitaldeals.utils.MailUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmailController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MailUtil mailUtil;

    @GetMapping("/purchaseemail")
    public String purchaseEmail(HttpSession session) {
        User user = (User) session.getAttribute("activeUser");

        String toEmail = user.getEmail();

        String subject = "Thank you for your purchase on Digital Deals!";

        double total = user.getCart().getTotalAmount();
        List<Order> ordersList = orderRepository.findAllByUser(user);
        String allOrders = ordersList.toString();

        String message = "You have purchased: " + "\n\n" + allOrders;

        mailUtil.sendEmail(toEmail, subject, message);

        return "redirect:/checkout";
    }
}
