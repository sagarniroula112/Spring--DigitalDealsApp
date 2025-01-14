package com.sagar.digitaldeals.repository;

import com.sagar.digitaldeals.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartitemdummyRepo extends JpaRepository<Cartitemdummy, Integer> {
    Cartitemdummy findCartitemdummyByProduct(Product product);
    Cartitemdummy findCartitemdummyByCartAndProduct(Cart cart, Product product);
    Cartitemdummy findCartitemdummyByCartAndProductAndUser(Cart cart, Product product, User user);
    Cartitemdummy findCartitemdummyByUserAndProduct(User user, Product product);
    List<Cartitemdummy> findAllByCart(Cart cart);
    List<Cartitemdummy> findAllByUser(User user);

    List<Cartitemdummy> findAllByOrder(Order order);

    void deleteAllByCart(Cart cart);
    void deleteAllByUser(User user);

    Cartitemdummy findCartitemdummyByCartAndProductAndId(Cart cart, Product product, int id);
}
