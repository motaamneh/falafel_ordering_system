package com.motaamneh.falafel.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AddOnCreateRequestDto(
        @NotBlank(message = "Add-on name cannot be blank")
        @Size(min = 2,max = 50,message = "Add-on name must be between 2 and 50 characters")
        String name,
        @NotBlank(message = "Price cannot be blank")
        @DecimalMin(value = "0.001", inclusive = false, message = "Price must be greater than 0")
        BigDecimal price
) {
    public AddOnCreateRequestDto{
        name = name !=null?name.trim():null;
    }
}
