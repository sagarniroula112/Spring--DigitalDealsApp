package com.sagar.digitaldeals.repository;

import com.sagar.digitaldeals.model.Order;
import com.sagar.digitaldeals.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderByUser(User user);
//    List<Order> findByIdContainingOrUserPhoneContaining(String orderId, String phone);

    List<Order> findAllByUser(User user);

}
