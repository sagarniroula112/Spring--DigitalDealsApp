package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.Category;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public String products(Model model) {

//        model.addAttribute("category", category);
        model.addAttribute("pList", productService.getAllProducts());

        return "Products"; // Return the products view
    }

}
