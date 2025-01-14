package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.Category;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.repository.CategoryRepository;
import com.sagar.digitaldeals.repository.ProductRepository;
import com.sagar.digitaldeals.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchSortController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/filter")
    public String filter(@RequestParam(value = "search", defaultValue = "") String searchQuery, @RequestParam("category") String category, @RequestParam(value = "sortBy", defaultValue = "none") String sortBy, Model model) {
        List<Product> filteredProducts;

        System.out.println("Selected Category: " + category);

        Category cat = categoryRepository.findByName(category);

        // Fetch products based on the category
        if ("All".equals(category)) {
            filteredProducts = productService.getAllProducts();
        } else {
            filteredProducts = productService.getAllByCategory(cat);
        }

        // Filter products by search query
        if (!searchQuery.isEmpty()) {
            filteredProducts = filteredProducts.stream()
                    .filter(product -> product.getTitle().toLowerCase().contains(searchQuery.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if ("lowToHigh".equals(sortBy)) {
            filteredProducts.sort(Comparator.comparingDouble(Product::getDiscountedPrice));
        } else if ("highToLow".equals(sortBy)) {
            filteredProducts.sort(Comparator.comparingDouble(Product::getDiscountedPrice).reversed());
        }

        // Add filtered products and selected category to the model
        model.addAttribute("pList", filteredProducts);
        model.addAttribute("searchQuery", searchQuery);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedSort", sortBy);

        return "Products"; // Return the products view
    }
}
