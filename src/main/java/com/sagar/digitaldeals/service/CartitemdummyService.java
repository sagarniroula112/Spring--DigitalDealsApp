package com.sagar.digitaldeals.service;

import com.sagar.digitaldeals.model.Cartitem;
import com.sagar.digitaldeals.model.Cartitemdummy;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.model.User;

import java.util.List;

public interface CartitemdummyService {
    void addCartitemdummy(Cartitemdummy Cartitemdummy);
    void deleteCartitemdummy(int id);
    void updateCartitemdummy(Cartitemdummy Cartitemdummy);
    Cartitemdummy getCartitemdummyById(int id);
    List<Cartitemdummy> getAllCartitemdummys();
    Cartitemdummy getCartitemdummyByProduct(Product product);
    void deleteAllByUser(User user);
}
