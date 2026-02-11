package com.motaamneh.falafel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDto(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8,message = "Password must be at least 8 characters")
        String password
) {
    public UserLoginRequestDto{
        email = email !=null?email.trim():null;
        password = password !=null?password.trim():null;

    }
}
