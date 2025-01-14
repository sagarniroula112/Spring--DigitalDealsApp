package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.*;
import com.sagar.digitaldeals.repository.CartitemRepository;
import com.sagar.digitaldeals.repository.CartitemdummyRepo;
import com.sagar.digitaldeals.repository.OrderRepository;
import com.sagar.digitaldeals.service.CartService;
import com.sagar.digitaldeals.service.CartitemdummyService;
import com.sagar.digitaldeals.service.ProductService;
import com.sagar.digitaldeals.utils.MailUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private CartitemdummyRepo cartitemdummyRepo;

    @Autowired
    private CartitemRepository cartitemRepository;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartitemdummyService cartitemdummyService;
    @Autowired
    private MailUtil mailUtil;

    @GetMapping("/checkout")
    @Transactional
    public String checkout(HttpSession session, Model model) {
        User user = (User) session.getAttribute("activeUser");

        if(user == null) {
            return "redirect:/login";
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setDeliveryStatus("Pending Approval");
        order.setUser(user);

        List<Cartitem> allCartItems = cartitemRepository.findAllByUser(user);

        Product[] productsArray = new Product[allCartItems.size()];

        for(int i = 0; i < allCartItems.size(); i++) {
            productsArray[i] = allCartItems.get(i).getProduct();
        }

        session.setAttribute("productsArray", productsArray);

        order.setProductsInOrder(List.of(productsArray));
        System.out.println(Arrays.toString(productsArray));
        order.setTotalAmount(user.getCart().getTotalAmount());

        if(user.getCart().getTotalAmount() == 0.0){
            return "redirect:/cart";
        }

//        if(orderRepository.findOrderByUser(user)!=null) {
//            Cart cart = user.getCart();
//            model.addAttribute("activeUser", user);
//            model.addAttribute("order", orderRepository.findOrderByUser(user));
//            model.addAttribute("cart", cart);
//            model.addAttribute("productsList", productsArray);
//            model.addAttribute("allCartItems", cartitemRepository.findAllByUser(user));
//
//            cart.setTotalAmount(0);
//            List<Cartitem> cartitems = user.getCart().getCartItem();
//
//            cartitemRepository.deleteAllByCart(cart);
//            cartService.updateCart(cart);
//
//            return "Checkout";
//        }
        orderRepository.save(order);

        List<Cartitemdummy> allCartItemsDummies = cartitemdummyRepo.findAllByUser(user);
        for(Cartitemdummy dummy : allCartItemsDummies) {
            if(dummy.getOrder() == null) {
                dummy.setOrder(order);
                cartitemdummyService.updateCartitemdummy(dummy);
            }
        }

        order.setCartitemdummies(allCartItemsDummies);

        int currentOrderId = order.getId();

        Cart cart = user.getCart();

        model.addAttribute("activeUser", user);
        model.addAttribute("order", order);
        model.addAttribute("cart", cart);
        model.addAttribute("productsList", productsArray);
        model.addAttribute("allCartItems", cartitemRepository.findAllByUser(user));

        cart.setTotalAmount(0);
//        cart.setCartItem(null);


        List<Cartitem> previousCartItems = cartitemRepository.findAllByUser(user);
        session.setAttribute("previousCartItems", previousCartItems);





        cartitemRepository.deleteAllByCart(cart);
        cartService.updateCart(cart);




//        String toEmail = user.getEmail();
//        String subject = "Thank you for your purchase on Digital Deals!";
//
//// Get the total amount from the cart
//        double total = orderRepository.findAllByUser(user).getLast().getTotalAmount();
//
//// Get the most recent order
//        Order latestOrder = orderRepository.findAllByUser(user).getLast();
//        List<Cartitemdummy> cartitemdummies = cartitemdummyRepo.findAllByOrder(order);
//
//// Start building the email message
//        StringBuilder message = new StringBuilder();
//        message.append("Dear ").append(user.getFname()).append(",\n\n");
//        message.append("Thank you for your recent purchase at Digital Deals! We're excited to confirm your order.\n\n");
//
//        message.append("Here are the details of your order:\n\n");
//        message.append("Order ID: ").append(latestOrder.getId()).append("\n");
//        message.append("Order Date: ").append(latestOrder.getOrderDate()).append("\n\n");
//
//        message.append("Products in your order:\n");
//
//        for (Cartitemdummy cartitem : cartitemdummies) {
//            Product product = cartitem.getProduct();
//            message.append("- ").append(product.getTitle())
//                    .append(" (x").append(cartitem.getPurchaseQuantity()) // Get quantity from Cartitemdummy
//                    .append(") - $").append(cartitem.getPurchaseAmount() / cartitem.getPurchaseQuantity()) // Calculate price per product
//                    .append("\n");
//        }
//
//        message.append("\nTotal Amount: $").append(total).append("\n\n");
//
//        message.append("We will notify you once your order has been shipped.\n\n");
//        message.append("Thank you for choosing Digital Deals!\n\n");
//        message.append("Best regards,\n");
//        message.append("The Digital Deals Team\n");
//
//        mailUtil.sendEmail(toEmail, subject, message.toString());





        return "Checkout";
    }

    @GetMapping("/yourorders")
    public String yourorders(Model model, HttpSession session) {

        User user = (User) session.getAttribute("activeUser");
        if(user == null) {
            return "redirect:/login";
        }

        List<Cartitem> previousCartItems = (List<Cartitem>) session.getAttribute("previousCartItems");
        if (previousCartItems != null) {
            model.addAttribute("previousCartItems", previousCartItems);
            session.removeAttribute("previousCartItems"); // Clear it after use
        }

        List<Order> ordersList = orderRepository.findAllByUser(user);
//        List<Orderitem> orderitemsList = orderitemService.getOrderitemsByUserAndOrder(user);


//        List<Cartitemdummy> cartitemdummies = cartitemdummyRepo.findAllByUser(user);
//        model.addAttribute()

//        model.addAttribute("orderItems", orderitemsList);

//        System.out.println(orderitemsList);

        ordersList.sort((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));
        model.addAttribute("ordersList", ordersList);
        return "YourOrders";
    }
}
