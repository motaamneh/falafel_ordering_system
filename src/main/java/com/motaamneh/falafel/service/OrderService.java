package com.motaamneh.falafel.service;

import com.motaamneh.falafel.entity.Order;
import com.motaamneh.falafel.model.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    Order createOrder(Integer userId, Integer restaurantId, String deliveryAddress,
                      Map<Integer, Integer> addOnQuantities, String notes);
    List<Order> getUserOrders(Integer userId);
    List<Order> getUserOrdersByStatus(Integer userId, OrderStatus status);

    // Restaurant operations
    List<Order> getRestaurantOrders(Integer restaurantId);
    List<Order> getRestaurantOrdersByStatus(Integer restaurantId, OrderStatus status);
    Order acceptOrder(Integer orderId, Integer restaurantId);
    Order rejectOrder(Integer orderId, Integer restaurantId);

    // Query operations
    Optional<Order> findOrderById(Integer id);
    List<Order> getAllOrders();
}
