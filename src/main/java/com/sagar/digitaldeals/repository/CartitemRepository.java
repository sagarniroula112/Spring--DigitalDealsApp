package com.sagar.digitaldeals.repository;

import com.sagar.digitaldeals.model.Cart;
import com.sagar.digitaldeals.model.Cartitem;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartitemRepository extends JpaRepository<Cartitem, Integer> {
    Cartitem findCartitemByProduct(Product product);
    Cartitem findCartitemByCartAndProduct(Cart cart, Product product);
    Cartitem findCartitemByUserAndProduct(User user, Product product);
    List<Cartitem> findAllByCart(Cart cart);
    List<Cartitem> findAllByUser(User user);

    void deleteAllByCart(Cart cart);
    void deleteAllByUser(User user);
}
