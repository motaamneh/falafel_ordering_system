package com.motaamneh.falafel.dto.response;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Integer addOnId,
        String addOnName,
        Integer quantity,
        BigDecimal priceAtOrder,
        BigDecimal lineTotal
) {
}
