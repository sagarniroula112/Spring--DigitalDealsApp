package com.sagar.digitaldeals.service;

import com.sagar.digitaldeals.model.Cartitem;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.model.User;

import java.util.List;

public interface CartitemService {
    void addCartitem(Cartitem cartitem);
    void deleteCartitem(int id);
    void updateCartitem(Cartitem cartitem);
    Cartitem getCartitemById(int id);
    List<Cartitem> getAllCartitems();
    Cartitem getCartitemByProduct(Product product);
    void deleteAllByUser(User user);
}
