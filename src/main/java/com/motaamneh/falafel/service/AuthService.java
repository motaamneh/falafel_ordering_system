package com.motaamneh.falafel.service;

import com.motaamneh.falafel.dto.request.RestaurantLoginRequestDto;
import com.motaamneh.falafel.dto.request.UserLoginRequestDto;
import com.motaamneh.falafel.dto.request.UserRegistrationDto;
import com.motaamneh.falafel.dto.response.LoginResponseDto;
import com.motaamneh.falafel.dto.response.RestaurantResponseDto;
import com.motaamneh.falafel.dto.response.UserResponseDto;
import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.entity.User;

import java.util.Optional;

public interface AuthService {
    // User authentication
    UserResponseDto registerUser(UserRegistrationDto userRegistrationDto);
    LoginResponseDto loginUser(UserLoginRequestDto userLoginRequestDto);

    // Restaurant authentication
    LoginResponseDto loginRestaurant(RestaurantLoginRequestDto restaurantLoginRequestDto);
}
