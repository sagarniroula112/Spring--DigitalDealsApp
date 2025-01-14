package com.sagar.digitaldeals.repository;

import com.sagar.digitaldeals.model.Category;
import com.sagar.digitaldeals.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategory(Category category);
}
