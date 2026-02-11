package com.motaamneh.falafel.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegistrationDto(

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "Password cannot be null")
        @Size(min = 8,message = "Password must be at least 8 characters")
        String password,
        @NotBlank(message = "Full name cannot be blank")
        @Size(min = 2,max = 100,message = "Full name must be between 2 and 100 characters")
        String fullName,
        @NotBlank(message = "Phone number cannot be blank")
        @Size(min = 8,max = 20,message = "Phone number must be between 8 and 20 characters")
        String phone
) {

        public UserRegistrationDto{

            email = email !=null?email.trim():null;
            fullName = fullName !=null?fullName.trim():null;
            password = password !=null?password.trim():null;

            if(password!=null && password.contains(" ")){
                    throw new IllegalArgumentException("Password cannot contain spaces");
            }

        }

}
