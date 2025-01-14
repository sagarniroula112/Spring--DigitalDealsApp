package com.sagar.digitaldeals.serviceimpl;

import com.sagar.digitaldeals.model.Category;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.repository.ProductRepository;
import com.sagar.digitaldeals.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository prodRepo;

    @Override
    public void addProduct(Product product) {
        prodRepo.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        prodRepo.deleteById(id);
    }

    @Override
    public void updateProduct(Product product) {
        prodRepo.save(product);
    }

    @Override
    public Product getProductById(int id) {
        return prodRepo.findById(id).get();
    }

    @Override
    public List<Product> getAllProducts() {
        return prodRepo.findAll();
    }

    @Override
    public List<Product> getAllByCategory(Category category) {
        return prodRepo.findAllByCategory(category);
    }
}
