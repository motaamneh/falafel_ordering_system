package com.motaamneh.falafel.service.impl;


import com.motaamneh.falafel.dto.request.OrderCreateRequestDto;
import com.motaamneh.falafel.dto.response.OrderResponseDto;
import com.motaamneh.falafel.dto.response.OrderSummaryDto;
import com.motaamneh.falafel.entity.*;
import com.motaamneh.falafel.exception.*;
import com.motaamneh.falafel.mapper.OrderMapper;
import com.motaamneh.falafel.model.OrderStatus;
import com.motaamneh.falafel.repository.AddOnRepository;
import com.motaamneh.falafel.repository.OrderRepository;
import com.motaamneh.falafel.repository.RestaurantRepository;
import com.motaamneh.falafel.repository.UserRepository;
import com.motaamneh.falafel.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddOnRepository addOnRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, AddOnRepository addOnRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.addOnRepository = addOnRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderResponseDto createOrder(Integer userId, OrderCreateRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (!user.getIsEnabled()) {
            throw new UserDisabledException("Account is disabled");
        }

        Restaurant restaurant = restaurantRepository.findById(dto.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        if (!restaurant.getIsActive()) {
            throw new RestaurantDisabledException("Restaurant is disabled");
        }

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(dto.deliveryAddress());
        order.setNotes(dto.notes());
        order.setStatus(OrderStatus.PENDING);

        Map<Integer, Integer> addOnQuantities =
                orderMapper.convertItemsToMap(dto.items());
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<Integer, Integer> entry : addOnQuantities.entrySet()) {
            Integer addOnId = entry.getKey();
            Integer quantity = entry.getValue();

            AddOn addOn = addOnRepository.findById(addOnId)
                    .orElseThrow(() -> new AddOnNotFoundException("Add-on not found"));
            if (!addOn.getIsAvailable()) {
                throw new InvalidAddOnException("Add-on is not available");
            }

            OrderAddOn orderAddOn = new OrderAddOn();
            orderAddOn.setOrder(order);
            orderAddOn.setAddOn(addOn);  // THIS WAS MISSING BEFORE - the bug fix
            orderAddOn.setQuantity(quantity);
            orderAddOn.setPriceAtOrder(addOn.getPrice());
            order.addOrderAddOn(orderAddOn);

            BigDecimal itemTotal =
                    addOn.getPrice().multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(itemTotal);
        }

        order.setTotalPrice(totalPrice);
        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    public List<OrderSummaryDto> getUserOrders(Integer userId) {
        return orderMapper.toSummaryDtoList(orderRepository.findByUserId(userId));
    }

    @Override
    public List<OrderSummaryDto> getUserOrdersByStatus(Integer userId, OrderStatus
            status) {
        return
                orderMapper.toSummaryDtoList(orderRepository.findByUserIdAndStatus(userId, status));
    }

    @Override
    public List<OrderResponseDto> getRestaurantOrders(Integer restaurantId) {
        return
                orderMapper.toResponseDtoList(orderRepository.findByRestaurantId(restaurantId));
    }

    @Override
    public List<OrderResponseDto> getRestaurantOrdersByStatus(Integer restaurantId,
                                                              OrderStatus status) {
        return orderMapper.toResponseDtoList(orderRepository.findByRestaurantIdAndStatus(restaurantId, status));
    }

    @Override
    public OrderResponseDto acceptOrder(Integer orderId, Integer restaurantId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (!order.getRestaurant().getId().equals(restaurantId)) {
            throw new UnauthorizedException("Restaurant not authorized");
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException("Order is not pending");
        }
        order.setStatus(OrderStatus.ACCEPTED);
        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto rejectOrder(Integer orderId, Integer restaurantId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (!order.getRestaurant().getId().equals(restaurantId)) {
            throw new UnauthorizedException("Restaurant not authorized");
        }
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException("Order is not pending");
        }
        order.setStatus(OrderStatus.DECLINED);
        return orderMapper.toResponseDto(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto findOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return orderMapper.toResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        return orderMapper.toResponseDtoList(orderRepository.findAll());
    }
}
