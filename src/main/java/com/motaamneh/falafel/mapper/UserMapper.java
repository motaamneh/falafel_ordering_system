package com.motaamneh.falafel.mapper;

import com.motaamneh.falafel.dto.request.UserRegistrationDto;
import com.motaamneh.falafel.dto.response.LoginResponseDto;
import com.motaamneh.falafel.dto.response.UserResponseDto;
import com.motaamneh.falafel.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getPhone(),
                user.getRole().name(),
                user.getIsEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public LoginResponseDto toLoginResponseDto(User user){
        return new LoginResponseDto(
          user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole().name(),
                "Login successful"

        );
    }

    public List<UserResponseDto> toResponseDto(List<User> users){
        return users.stream().map(this::toResponseDto).toList();
    }


}
