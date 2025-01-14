package com.sagar.digitaldeals.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="cartitemdummy_table")
public class Cartitemdummy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    private int purchaseQuantity;
    private double purchaseAmount;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime dummyCreatedDateTime;

    @Override
    public String toString() {
        return "Cartitemdummy{" +
                "dummyCreatedDateTime=" + dummyCreatedDateTime +
                ", id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", purchaseQuantity=" + purchaseQuantity +
                ", purchaseAmount=" + purchaseAmount +
                '}';
    }

    public LocalDateTime getDummyCreatedDateTime() {
        return dummyCreatedDateTime;
    }

    public void setDummyCreatedDateTime(LocalDateTime dummyCreatedDateTime) {
        this.dummyCreatedDateTime = dummyCreatedDateTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    public double getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(double purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
