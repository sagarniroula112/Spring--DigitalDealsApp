package com.sagar.digitaldeals.service;

import com.sagar.digitaldeals.model.Category;
import com.sagar.digitaldeals.model.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    void deleteProduct(int id);
    void updateProduct(Product product);
    Product getProductById(int id);
    List<Product> getAllProducts();
    List<Product> getAllByCategory(Category category);
}
