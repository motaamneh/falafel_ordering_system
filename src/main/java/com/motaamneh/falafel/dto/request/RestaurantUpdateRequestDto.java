package com.motaamneh.falafel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RestaurantUpdateRequestDto(
        @NotBlank(message = "Restaurant name cannot be blank")
        @Size(min = 2, max = 100,message = "Restaurant name must be at least 2 characters")
        String name,
        @NotBlank(message = "Address cannot be blank")
        @Size(min = 2,message = "Address must be at least 2 characters")
        String address,
        @NotBlank(message = "Phone number cannot be blank")
        @Size(min = 8,max = 20,message = "Phone number must be between 8 and 20 characters")
        String phone
) {
    public RestaurantUpdateRequestDto{
        name = name !=null?name.trim():null;
        address = address !=null?address.trim():null;
        phone = phone !=null?phone.trim():null;
    }
}
