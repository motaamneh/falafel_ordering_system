package com.motaamneh.falafel.controller;

import com.motaamneh.falafel.dto.request.OrderCreateRequestDto;
import com.motaamneh.falafel.dto.response.OrderResponseDto;
import com.motaamneh.falafel.dto.response.OrderSummaryDto;
import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.security.JwtUtil;
import com.motaamneh.falafel.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final JwtUtil jwtUtil;

    public OrderController(OrderService orderService, JwtUtil jwtUtil) {
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
    }

    private Integer getCurrentId(HttpServletRequest request){
        String token = request.getHeader("Authorization").substring(7);
        return jwtUtil.extractId(token);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderCreateRequestDto dto,
                                                        HttpServletRequest request) {
        Integer userId = getCurrentId(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(userId,dto));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrderSummaryDto>> getUserOrders(@PathVariable Integer userId,
                                                               HttpServletRequest request) {
        Integer callerId = getCurrentId(request);
        if(!callerId.equals(userId)){
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(orderService.getUserOrders(userId));
        
    }

    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<List<OrderResponseDto>> getRestaurantOrders(@PathVariable Integer restaurantId,
                                                                      HttpServletRequest request) {
        Integer callerId = getCurrentId(request);
        if(!callerId.equals(restaurantId)){
            return ResponseEntity.status(403).build();

        }
        return ResponseEntity.ok(orderService.getRestaurantOrders(restaurantId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','RESTAURANT')")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    // PATCH /api/orders/{id}/accept — Restaurant accepts an order
    @PatchMapping("/{id}/accept")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<OrderResponseDto> acceptOrder(@PathVariable Integer id,
                                                        HttpServletRequest request) {
        Integer restaurantId = getCurrentId(request);
        return ResponseEntity.ok(orderService.acceptOrder(id, restaurantId));
    }

    // PATCH /api/orders/{id}/reject — Restaurant rejects an order
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<OrderResponseDto> rejectOrder(@PathVariable Integer id,
                                                        HttpServletRequest request) {
        Integer restaurantId = getCurrentId(request);
        return ResponseEntity.ok(orderService.rejectOrder(id, restaurantId));
    }




}
