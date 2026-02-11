package com.motaamneh.falafel.service.impl;


import com.motaamneh.falafel.entity.*;
import com.motaamneh.falafel.exception.*;
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

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, RestaurantRepository restaurantRepository, AddOnRepository addOnRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.addOnRepository = addOnRepository;
    }

    @Override
    public Order createOrder(Integer userId, Integer restaurantId, String deliveryAddress, Map<Integer, Integer> addOnQuantities, String notes) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        if(!user.getIsEnabled()){
            throw new UserDisabledException("Account is disabled");
        }
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RuntimeException("Restaurant not found"));
        if(!restaurant.getIsActive()){
            throw new RestaurantDisabledException("Restaurant is disabled");
        }

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(deliveryAddress);
        order.setNotes(notes);
        order.setStatus(OrderStatus.PENDING);


        BigDecimal totalPrice = BigDecimal.ZERO;

        for(Map.Entry<Integer, Integer> entry : addOnQuantities.entrySet()){
            Integer addOnId = entry.getKey();
            Integer quantity = entry.getValue();

            AddOn addOn = addOnRepository.findById(addOnId).orElseThrow(()-> new AddOnNotFoundException("Add-on not found"));
            if(!addOn.getIsAvailable()){
                throw new InvalidAddOnException("Add-on is not available");

            }
            OrderAddOn orderAddOn = new OrderAddOn();
            orderAddOn.setOrder(order);
            orderAddOn.setQuantity(quantity);
            orderAddOn.setPriceAtOrder(addOn.getPrice());
            order.addOrderAddOn(orderAddOn);

            BigDecimal itemTotal = addOn.getPrice().multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(itemTotal);


        }
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);

    }

    @Override
    public List<Order> getUserOrders(Integer userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> getUserOrdersByStatus(Integer userId, OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<Order> getRestaurantOrders(Integer restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<Order> getRestaurantOrdersByStatus(Integer restaurantId, OrderStatus status) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, status);
    }

    @Override
    public Order acceptOrder(Integer orderId, Integer restaurantId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order not found"));
        if(!order.getRestaurant().getId().equals(restaurantId)){
            throw new UnauthorizedException("Restaurant not authorized");

        }
        if(order.getStatus()!=OrderStatus.PENDING){
            throw new InvalidOrderStatusException("Order is not pending");

        }

        order.setStatus(OrderStatus.ACCEPTED);
        return orderRepository.save(order);
    }

    @Override
    public Order rejectOrder(Integer orderId, Integer restaurantId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order not found"));
        if(!order.getRestaurant().getId().equals(restaurantId)){
            throw new UnauthorizedException("User not authorized");
        }
        if(order.getStatus()!=OrderStatus.PENDING){
            throw new InvalidOrderStatusException("Order is not pending");
        }
        order.setStatus(OrderStatus.DECLINED);
        return orderRepository.save(order);


    }

    @Override
    public Optional<Order> findOrderById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
