package com.sagar.digitaldeals.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="cart_table")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    private List<Cartitem> cartItem;

    private double totalAmount;
    private boolean checkedOut;

    public Cart(){
        this.cartItem = null;
        this.totalAmount = 0;
        this.checkedOut = false;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + user +
                ", totalAmount=" + totalAmount +
                '}';
    }



    public boolean isCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public List<Cartitem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<Cartitem> cartItem) {
        this.cartItem = cartItem;
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
        double epsilon = 1e-10; // Define a small threshold
        return Math.abs(totalAmount) < epsilon ? 0.0 : totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        double epsilon = 1e-10; // Define a small threshold
        this.totalAmount = Math.abs(totalAmount) < epsilon ? 0.0 : totalAmount;
    }
}
