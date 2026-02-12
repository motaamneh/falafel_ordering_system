package com.motaamneh.falafel.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AddOnResponseDto(
        Integer id,
        Integer restaurantId,
        String name,
        BigDecimal price,
        Boolean isAvailable,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
