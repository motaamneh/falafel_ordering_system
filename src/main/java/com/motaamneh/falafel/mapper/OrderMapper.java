package com.motaamneh.falafel.mapper;


import com.motaamneh.falafel.dto.request.OrderItemRequestDto;
import com.motaamneh.falafel.dto.response.OrderItemResponseDto;
import com.motaamneh.falafel.dto.response.OrderResponseDto;
import com.motaamneh.falafel.dto.response.OrderSummaryDto;
import com.motaamneh.falafel.entity.Order;
import com.motaamneh.falafel.entity.OrderAddOn;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    //Orderitemresponse
    //orderresponse
    //ordersummaryresponse


    public OrderItemResponseDto toOrderItemDto(OrderAddOn orderAddOn) {
        /*Integer addOnId,
        String addOnName,
        Integer quantity,
        BigDecimal priceAtOrder,
        BigDecimal lineTotal*/
        // we have to get the total price based on the quantity of the item
        BigDecimal lineTotal = orderAddOn.getPriceAtOrder().multiply((BigDecimal.valueOf(orderAddOn.getQuantity())));
        return new OrderItemResponseDto(orderAddOn.getId(), orderAddOn.getAddOn().getName(), orderAddOn.getQuantity(), orderAddOn.getPriceAtOrder(), lineTotal

        );
    }

    public List<OrderItemResponseDto> toOrderItemDtoList(List<OrderAddOn> orderAddOns) {
        return orderAddOns.stream().map(this::toOrderItemDto).toList();
    }

    public OrderResponseDto toResponseDto(Order order) {
        /*
        *
        Integer id,
        Integer userId,
        String userFullName,
        Integer restaurantId,
        String restaurantName,
        String status,
        BigDecimal totalPrice,
        String deliveryAddress,
        String notes,
        List<OrderItemResponseDto> items,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
        * */
        return new OrderResponseDto(
                order.getId(),
                order.getUser().getId(),
                order.getUser().getFullName(),
                order.getRestaurant().getId(),
                order.getRestaurant().getName(),
                order.getStatus().name(),
                order.getTotalPrice(),
                order.getDeliveryAddress(),
                order.getNotes(),
                toOrderItemDtoList(order.getOrderAddOns()),
                order.getCreatedAt(),
                order.getUpdatedAt()


        );
    }

    public List<OrderResponseDto> toResponseDtoList(List<Order> orders) {
        return orders.stream().map(this::toResponseDto).toList();
    }

    public OrderSummaryDto toSummaryDto(Order order) {

        //        Integer id,
        //        String restaurantName,
        //        String status,
        //        BigDecimal totalPrice,
        //        LocalDateTime createdAt
        return new OrderSummaryDto(
                order.getId(),
                order.getRestaurant().getName(),
                order.getStatus().name(),
                order.getTotalPrice(),
                order.getCreatedAt()
        );
    }
    public List<OrderSummaryDto> toSummaryDtoList(List<Order> orders) {
        return orders.stream().map(this::toSummaryDto).toList();
    }

    public Map<Integer, Integer> convertItemsToMap(List<OrderItemRequestDto> items) {
        return items.stream()
                .collect(Collectors.toMap(
                        OrderItemRequestDto::addOnId,
                        OrderItemRequestDto::quantity
                ));
    }

}
