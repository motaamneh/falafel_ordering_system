package com.motaamneh.falafel.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AddOnUpdateRequestDto(
        @NotBlank(message = "Add-on name cannot be blank")
        @Size(min = 2, max = 50, message = "Add-on name must be between 2 and 50 characters")
        String name,

        @NotNull(message = "Price cannot be null")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,

        @NotNull(message = "Availability status cannot be null")
        Boolean isAvailable
) {
    public AddOnUpdateRequestDto {
        name = name != null ? name.trim() : null;
    }
}
