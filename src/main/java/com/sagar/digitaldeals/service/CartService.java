package com.sagar.digitaldeals.service;

import com.sagar.digitaldeals.model.Cart;

import java.util.List;

public interface CartService {
    Cart addCart(Cart cart);
    void deleteCart(int id);
    void updateCart(Cart cart);
    Cart getCartById(int id);
    List<Cart> getAllCarts();
}

