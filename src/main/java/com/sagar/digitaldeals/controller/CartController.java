package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.*;
import com.sagar.digitaldeals.repository.CartRepository;
import com.sagar.digitaldeals.repository.CartitemRepository;
import com.sagar.digitaldeals.repository.CartitemdummyRepo;
import com.sagar.digitaldeals.repository.OrderRepository;
import com.sagar.digitaldeals.service.CartService;
import com.sagar.digitaldeals.service.CartitemService;
import com.sagar.digitaldeals.service.CartitemdummyService;
import com.sagar.digitaldeals.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartitemService cartitemService;
    @Autowired
    private CartitemRepository cartitemRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartitemdummyService cartitemdummyService;
    @Autowired
    private CartitemdummyRepo cartitemdummyRepo;
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {
        User activeUser = (User) session.getAttribute("activeUser");

        if(activeUser != null) {
            model.addAttribute("totalCartPrice", activeUser.getCart().getTotalAmount());
            model.addAttribute("allCartItems", cartitemRepository.findAllByUser(activeUser));
            return "Cart";
        }

        return "redirect:/login";
    }

    @PostMapping("/addtocart")
    public String addtocart(@RequestParam int productId, HttpSession session, Model model, @RequestParam("quantity") Integer quantity) {
        Product product = productService.getProductById(productId);
        User activeUser = (User) session.getAttribute("activeUser");

        if(activeUser == null) {
            return "redirect:/login";
        }

        Cart cart = activeUser.getCart();

        // Check if the product already exists in the cart
        if (cartitemRepository.findCartitemByCartAndProduct(cart, product) != null) {
            model.addAttribute("totalCartPrice", cart.getTotalAmount());
            return "redirect:/products";
        }

        // Create new cart item and add it to the cart
        Cartitem cartitem = new Cartitem();
        cartitem.setUser(activeUser);
        cartitem.setProduct(product);
        cartitem.setCart(cart);
        cartitem.setPurchaseAmount(product.getDiscountedPrice()*quantity);
        cartitem.setPurchaseQuantity(quantity);

        Cartitemdummy cartitemdummy = new Cartitemdummy();
        cartitemdummy.setUser(activeUser);
        cartitemdummy.setProduct(product);
        cartitemdummy.setCart(cart);
        cartitemdummy.setPurchaseAmount(product.getDiscountedPrice()*quantity);
        cartitemdummy.setPurchaseQuantity(quantity);
        cartitemdummy.setDummyCreatedDateTime(LocalDateTime.now());

        // Update total cart amount
        cartitemService.addCartitem(cartitem);
        cartitemdummyService.addCartitemdummy(cartitemdummy);
        cart.setTotalAmount(cart.getTotalAmount() + cartitem.getPurchaseAmount());

        cartService.updateCart(cart);

        return "redirect:/products";
    }

    @GetMapping("/deleteCartItem")
        private String deleteCartItem(@RequestParam int productId, Model model, HttpSession session) {
        User activeUser = (User) session.getAttribute("activeUser");
        System.out.println(productId);

        Product product = productService.getProductById(productId);
        Cart cart = activeUser.getCart();
        Cartitem ci = cartitemRepository.findCartitemByCartAndProduct(cartRepository.findCartByUser(activeUser), productService.getProductById(productId));
        Cartitemdummy cid = cartitemdummyRepo.findCartitemdummyByCartAndProductAndId(cart, product, ci.getId());

        cart.setTotalAmount(cart.getTotalAmount() - ci.getProduct().getDiscountedPrice()*ci.getPurchaseQuantity());
        cartService.updateCart(cart);

        model.addAttribute("totalCartPrice", activeUser.getCart().getTotalAmount());
        cartitemService.deleteCartitem(ci.getId());
        cartitemdummyService.deleteCartitemdummy(cid.getId());
        return "redirect:/cart";
    }

    @PostMapping("/updateCartItem")
    private String updateCartItem(@RequestParam int productId, @RequestParam int quantity, HttpSession session) {
        User activeUser = (User) session.getAttribute("activeUser");

        Cart cart = activeUser.getCart();
        Product product = productService.getProductById(productId);

        Cartitem ci = cartitemRepository.findCartitemByCartAndProduct(cart, product);
        Cartitemdummy cid = cartitemdummyRepo.findCartitemdummyByCartAndProductAndId(cart, product, ci.getId());

        int initialQuantity = ci.getPurchaseQuantity();
        int initialQuantityD = cid.getPurchaseQuantity();

        ci.setPurchaseQuantity(quantity);
        cid.setPurchaseQuantity(quantity);

        ci.setPurchaseAmount(product.getDiscountedPrice()*quantity);
        cid.setPurchaseAmount(product.getDiscountedPrice()*quantity);

        cartitemService.updateCartitem(ci);
        cartitemdummyService.updateCartitemdummy(cid);

        if(initialQuantity < quantity) {
            cart.setTotalAmount(cart.getTotalAmount() + product.getDiscountedPrice()*(quantity-initialQuantity));
            cartService.updateCart(cart);
        } else {
             cart.setTotalAmount(cart.getTotalAmount() - product.getDiscountedPrice()*(initialQuantity-quantity));
        }

        return "redirect:/cart";
    }
}
