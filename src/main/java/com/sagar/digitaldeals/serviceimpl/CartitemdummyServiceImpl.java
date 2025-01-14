package com.sagar.digitaldeals.serviceimpl;

import com.sagar.digitaldeals.model.Cartitemdummy;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.model.User;
import com.sagar.digitaldeals.repository.CartitemdummyRepo;
import com.sagar.digitaldeals.repository.CartitemdummyRepo;
import com.sagar.digitaldeals.service.CartitemdummyService;
//import com.sagar.digitaldeals.service.CartitemdummydummyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartitemdummyServiceImpl implements CartitemdummyService {

    @Autowired
    private CartitemdummyRepo CartitemdummyRepository;

    @Override
    public void addCartitemdummy(Cartitemdummy Cartitemdummy) {
        CartitemdummyRepository.save(Cartitemdummy);
    }

    @Override
    public void deleteCartitemdummy(int id) {
        CartitemdummyRepository.deleteById(id);
    }

    @Override
    public void updateCartitemdummy(Cartitemdummy Cartitemdummy) {
        CartitemdummyRepository.save(Cartitemdummy);
    }

    @Override
    public Cartitemdummy getCartitemdummyById(int id) {
        return CartitemdummyRepository.findById(id).get();
    }

    @Override
    public List<Cartitemdummy> getAllCartitemdummys() {
        return CartitemdummyRepository.findAll();
    }

    @Override
    public Cartitemdummy getCartitemdummyByProduct(Product product) {
        return CartitemdummyRepository.findCartitemdummyByProduct(product);
    }

    @Override
    public void deleteAllByUser(User user) {
        CartitemdummyRepository.deleteAllByUser(user);
    }
}
