package com.motaamneh.falafel.repository;

import com.motaamneh.falafel.entity.Order;
import com.motaamneh.falafel.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // User views their own orders
    List<Order> findByUserId(Long userId);

    // Restaurant views orders for their restaurant
    List<Order> findByRestaurantId(Long restaurantId);

    // Restaurant views pending orders only
    List<Order> findByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);

    // User views their orders by status
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);

    // Optional: Find all orders by status (for admin)
    List<Order> findByStatus(OrderStatus status);


}
