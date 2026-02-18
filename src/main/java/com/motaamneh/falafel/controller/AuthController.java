package com.motaamneh.falafel.controller;

import com.motaamneh.falafel.dto.request.RestaurantLoginRequestDto;
import com.motaamneh.falafel.dto.request.UserLoginRequestDto;
import com.motaamneh.falafel.dto.request.UserRegistrationDto;
import com.motaamneh.falafel.dto.response.LoginResponseDto;
import com.motaamneh.falafel.dto.response.UserResponseDto;
import com.motaamneh.falafel.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "User and Restaurant authentication endpoints")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRegistrationDto dto) {
        UserResponseDto responseDto = authService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/login/user")
    public ResponseEntity<LoginResponseDto> loginUser(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        LoginResponseDto response = authService.loginUser(userLoginRequestDto);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/login/restaurant")
    public ResponseEntity<LoginResponseDto> loginRestaurant(@RequestBody RestaurantLoginRequestDto restaurantLoginRequestDto) {
        LoginResponseDto response = authService.loginRestaurant(restaurantLoginRequestDto);
        return ResponseEntity.ok(response);
    }


}
