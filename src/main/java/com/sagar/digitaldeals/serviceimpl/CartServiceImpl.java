package com.sagar.digitaldeals.serviceimpl;

import com.sagar.digitaldeals.model.Cart;
import com.sagar.digitaldeals.repository.CartRepository;
import com.sagar.digitaldeals.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addCart(Cart cart) {
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public void deleteCart(int id) {
        cartRepository.deleteById(id);
    }

    @Override
    public void updateCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartById(int id) {
        return cartRepository.findById(id).get();
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }
}
