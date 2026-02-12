package com.motaamneh.falafel.dto.response;

import java.time.LocalDateTime;

public record RestaurantResponseDto(
        Integer id,
        String name,
        String email,
        String address,
        String phone,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
