package com.sagar.digitaldeals.controller;

import com.sagar.digitaldeals.model.Category;
import com.sagar.digitaldeals.model.Order;
import com.sagar.digitaldeals.model.Product;
import com.sagar.digitaldeals.model.User;
import com.sagar.digitaldeals.repository.CategoryRepository;
import com.sagar.digitaldeals.repository.OrderRepository;
import com.sagar.digitaldeals.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderRepository orderRepository;

    private boolean isAdmin(HttpSession session) {
        User currentUser = (User) session.getAttribute("activeUser");
        return currentUser != null && "admin".equals(currentUser.getRole());
    }

    @GetMapping("/admin/viewproducts")
    public String adminviewproducts(Model model) {
        model.addAttribute("pList", productService.getAllProducts());

        return "AdminViewProducts";
    }

    @GetMapping("/admin/searchorders")
    public String searchOrders(@RequestParam("query") String query, Model model, HttpSession session) {
        if (isAdmin(session)) {
            // Fetch orders based on orderId or phone number
            List<Order> searchResults = orderRepository.findAll().stream()
                    .filter(order -> String.valueOf(order.getId()).contains(query) ||
                            order.getUser().getPhone().contains(query))
                    .collect(Collectors.toList());

            model.addAttribute("orderList", searchResults);
            return "AdminOrders";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/searchproducts")
    public String searchProducts(@RequestParam("query") String query, Model model, HttpSession session) {
        if (isAdmin(session)) {
            // Fetch orders based on orderId or phone number
            List<Product> searchResults = productService.getAllProducts().stream()
                    .filter(product -> product.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());

            model.addAttribute("productList", searchResults);
            return "AdminProducts";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/manageproducts")
    public String manageProducts(Model model, HttpSession session) {
        if (isAdmin(session)) {
            model.addAttribute("productList", productService.getAllProducts());
            return "AdminProducts";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/manageorders")
    public String manageOrders(Model model, HttpSession session) {
        if (isAdmin(session)) {
            List<Order> orderList = orderRepository.findAll();

            // Sort orders by orderDate in descending order
            List<Order> sortedOrders = orderList.stream()
                    .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()))
                    .collect(Collectors.toList());

            model.addAttribute("orderList", sortedOrders);
            return "AdminOrders";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/edit-product/{id}")
    public String editProduct(Model model, @PathVariable int id, HttpSession session) {
        if (isAdmin(session)) {
            model.addAttribute("product", productService.getProductById(id));
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            return "AdminEditProduct";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/order-details/{id}")
    public String orderDetails(Model model, @PathVariable int id, HttpSession session) {
        if (isAdmin(session)) {
            Order order = orderRepository.findById(id).orElse(null);
            if (order != null) {
                model.addAttribute("order", order);
                return "AdminViewOrderDetail";
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/update-order-status/{id}")
    public String updateOrderStatus(Model model, @PathVariable int id, HttpSession session) {
        if (isAdmin(session)) {
            Order order = orderRepository.findById(id).orElse(null);
            if (order != null) {
                model.addAttribute("order", order);
                model.addAttribute("statuses", Arrays.asList("Pending Approval", "Approved", "On your way", "Delivered", "Cancelled"));
                return "AdminEditOrderStatus";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/update-order-status/{id}")
    public String updateOrderStatus(@PathVariable int id, @RequestParam("status") String status, HttpSession session) {
        if (isAdmin(session)) {
            Order order = orderRepository.findById(id).orElse(null);
            if (order != null) {
                order.setDeliveryStatus(status);
                orderRepository.save(order);
            }
            return "redirect:/admin/manageorders";
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/update-product")
    public String updateProduct(
            @RequestParam("id") int id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("discountPercent") double discountPercent,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("price") double price,
            HttpSession session) {
        if (isAdmin(session)) {
            Product product = productService.getProductById(id);
            if (product != null) {
                product.setTitle(name);
                product.setDescription(description);
                product.setCategory(categoryRepository.findByName(category));
                product.setDiscount(discountPercent);
                product.setStockQuantity(stockQuantity);
                product.setImage(imageUrl);
                product.setPrice(price);
                product.setDiscountedPrice(((100 - discountPercent) / 100) * price);
                productService.updateProduct(product);
            }
            return "redirect:/admin/manageproducts";
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/delete-product")
    public String deleteProduct(@RequestParam("productId") int id, HttpSession session) {
        if (isAdmin(session)) {
            productService.deleteProduct(id);
            return "redirect:/admin/manageproducts";
        }
        return "redirect:/login";
    }

    @GetMapping("/admin/add-product")
    public String addProduct(Model model, HttpSession session) {
        if (isAdmin(session)) {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categories", categories);
            return "AdminAddProduct";
        }
        return "redirect:/login";
    }

    @PostMapping("/admin/add-product")
    public String afterAddProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("discountPercent") double discountPercent,
            @RequestParam("stockQuantity") int stockQuantity,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("price") double price,
            HttpSession session) {
        if (isAdmin(session)) {
            Product product = new Product();
            product.setTitle(name);
            product.setDescription(description);
            product.setCategory(categoryRepository.findByName(category));
            product.setDiscount(discountPercent);
            product.setStockQuantity(stockQuantity);
            product.setImage(imageUrl);
            product.setPrice(price);
            product.setDiscountedPrice(((100 - discountPercent) / 100) * price);
            productService.addProduct(product);
            return "redirect:/admin/manageproducts";
        }
        return "redirect:/login";
    }
}
