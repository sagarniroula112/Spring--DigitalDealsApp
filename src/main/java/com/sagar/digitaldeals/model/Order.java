package com.sagar.digitaldeals.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    List<Cartitem> cartitems;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productsInOrder;

    @ManyToMany
    @JoinTable(
            name = "order_cartitemdummies",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "cartitemdummy_id")
    )
    private List<Cartitemdummy> cartitemdummies;

    @ElementCollection
    private List<Integer> quantity;

    private double totalAmount;
    private LocalDateTime orderDate;
    private String deliveryStatus;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user.getId() + // Include only the ID to avoid circular reference
                ", totalAmount=" + totalAmount +
                ", orderDate=" + orderDate +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }


    public List<Cartitem> getCartitems() {
        return cartitems;
    }

    public void setCartitems(List<Cartitem> cartitems) {
        this.cartitems = cartitems;
    }

    public List<Cartitemdummy> getCartitemdummies() {
        return cartitemdummies;
    }

    public void setCartitemdummies(List<Cartitemdummy> cartitemdummies) {
        this.cartitemdummies = cartitemdummies;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }

    public List<Product> getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(List<Product> productsInOrder) {
        this.productsInOrder = productsInOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
