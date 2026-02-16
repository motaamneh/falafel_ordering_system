package com.motaamneh.falafel.service;

import com.motaamneh.falafel.dto.request.OrderCreateRequestDto;
import com.motaamneh.falafel.dto.response.OrderResponseDto;
import com.motaamneh.falafel.dto.response.OrderSummaryDto;
import com.motaamneh.falafel.entity.Order;
import com.motaamneh.falafel.model.OrderStatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    OrderResponseDto createOrder(Integer userId, OrderCreateRequestDto dto);
    List<OrderSummaryDto> getUserOrders(Integer userId);
    List<OrderSummaryDto> getUserOrdersByStatus(Integer userId, OrderStatus status);

    // Restaurant operations
    List<OrderResponseDto> getRestaurantOrders(Integer restaurantId);
    List<OrderResponseDto> getRestaurantOrdersByStatus(Integer restaurantId, OrderStatus status);
    OrderResponseDto acceptOrder(Integer orderId, Integer restaurantId);
    OrderResponseDto rejectOrder(Integer orderId, Integer restaurantId);

    // Query operations
    OrderResponseDto findOrderById(Integer id);
    List<OrderResponseDto> getAllOrders();
}
