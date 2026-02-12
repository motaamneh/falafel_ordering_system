package com.motaamneh.falafel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
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
) {
}
