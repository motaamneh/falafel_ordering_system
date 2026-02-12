package com.motaamneh.falafel.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDto(
        @NotNull(message = "Add-on ID cannot be null")
        Integer addOnId,

        @NotNull(message = "Quantity cannot be null")
        @Min(value = 1, message = "Quantity must be at least 1")
        Integer quantity) { }
