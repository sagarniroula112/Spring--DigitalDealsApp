package com.sagar.digitaldeals.serviceimpl;

import com.sagar.digitaldeals.model.Cartitem;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.model.User;
import com.sagar.digitaldeals.repository.CartitemRepository;
import com.sagar.digitaldeals.service.CartitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartitemServiceImpl implements CartitemService {

    @Autowired
    private CartitemRepository cartitemRepository;

    @Override
    public void addCartitem(Cartitem cartitem) {
        cartitemRepository.save(cartitem);
    }

    @Override
    public void deleteCartitem(int id) {
        cartitemRepository.deleteById(id);
    }

    @Override
    public void updateCartitem(Cartitem cartitem) {
        cartitemRepository.save(cartitem);
    }

    @Override
    public Cartitem getCartitemById(int id) {
        return cartitemRepository.findById(id).get();
    }

    @Override
    public List<Cartitem> getAllCartitems() {
        return cartitemRepository.findAll();
    }

    @Override
    public Cartitem getCartitemByProduct(Product product) {
        return cartitemRepository.findCartitemByProduct(product);
    }

    @Override
    public void deleteAllByUser(User user) {
        cartitemRepository.deleteAllByUser(user);
    }
}
