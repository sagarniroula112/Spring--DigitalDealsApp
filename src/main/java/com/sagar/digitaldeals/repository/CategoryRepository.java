package com.sagar.digitaldeals.repository;

import com.sagar.digitaldeals.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);
}
