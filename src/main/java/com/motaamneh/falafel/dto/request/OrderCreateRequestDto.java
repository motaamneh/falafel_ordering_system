package com.motaamneh.falafel.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderCreateRequestDto(
        @NotNull(message = "Restaurant ID cannot be null")
        Integer restaurantId,
        @NotNull(message = "Delivery address cannot be null")
        @Size(min = 5,message = "Delivery address must be at least 5 characters")
        String deliveryAddress,

        String notes,
        @NotEmpty(message = "Order items cannot be empty")
        @Valid
        List<OrderItemRequestDto> items

) {
    public OrderCreateRequestDto{
        deliveryAddress = deliveryAddress !=null?deliveryAddress.trim():null;
        notes = notes !=null?notes.trim():null;
    }
}
