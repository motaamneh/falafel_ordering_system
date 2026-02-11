package com.motaamneh.falafel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDto(
        @NotBlank(message = "Full name cannot be blank")
        @Size(min = 2,max=100,message = "Full name must be at least 2 characters")
        String fullName,
        @NotBlank(message = "Phone number cannot be blank")
        @Size(min = 8,max = 20,message = "Phone number must be between 8 and 20 characters")
        String phone
) {
    public UserUpdateRequestDto{
        fullName = fullName !=null?fullName.trim():null;
        phone = phone !=null?phone.trim():null;
    }
}
