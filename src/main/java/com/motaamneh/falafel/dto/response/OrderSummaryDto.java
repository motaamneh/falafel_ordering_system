package com.motaamneh.falafel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderSummaryDto(
        Integer id,
        String restaurantName,
        String status,
        BigDecimal totalPrice,
        LocalDateTime createdAt
) {
}
