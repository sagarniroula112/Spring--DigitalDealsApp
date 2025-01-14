package com.sagar.digitaldeals.repository;

import com.sagar.digitaldeals.model.Cart;
import com.sagar.digitaldeals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findCartByUser(User user);
}
