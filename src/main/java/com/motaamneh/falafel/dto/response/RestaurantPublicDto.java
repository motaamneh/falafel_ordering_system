package com.motaamneh.falafel.dto.response;

// For users browsing restaurants

public record RestaurantPublicDto(
        Integer id,
        String name,
        String address,
        String phone,
        Boolean isActive
) {
}
