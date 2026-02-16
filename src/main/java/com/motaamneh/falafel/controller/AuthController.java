package com.motaamneh.falafel.controller;

import com.motaamneh.falafel.dto.request.RestaurantLoginRequestDto;
import com.motaamneh.falafel.dto.request.UserLoginRequestDto;
import com.motaamneh.falafel.dto.request.UserRegistrationDto;
import com.motaamneh.falafel.dto.response.LoginResponseDto;
import com.motaamneh.falafel.dto.response.RestaurantResponseDto;
import com.motaamneh.falafel.dto.response.UserResponseDto;
import com.motaamneh.falafel.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService){
       this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto dto){

        UserResponseDto responseDto = authService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }
    @PostMapping("/login/user")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody UserLoginRequestDto dto){
        Optional<UserResponseDto> userResponseDto = authService.loginUser(dto);
        if(userResponseDto.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserResponseDto userDto = userResponseDto.get();
        LoginResponseDto loginResponseDto = new LoginResponseDto(
          userDto.id(),
          userDto.email(),
          userDto.fullName(),
          userDto.role(),
          "Login successful"
        );
        return ResponseEntity.ok(loginResponseDto);
    }
    @PostMapping("/login/restaurant")
    public ResponseEntity<LoginResponseDto> loginRestaurant(@Valid @RequestBody RestaurantLoginRequestDto restaurantLoginRequestDto){
        Optional<RestaurantResponseDto> restaurantResponseDtoOptional = authService.loginRestaurant(restaurantLoginRequestDto);
        if(restaurantResponseDtoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        RestaurantResponseDto restaurantDto = restaurantResponseDtoOptional.get();
        LoginResponseDto loginResponse   = new LoginResponseDto(
                restaurantDto.id(),
                restaurantDto.email(),
                restaurantDto.name(),
                "RESTAURANT",
                "Login successful"

        );
        return ResponseEntity.ok(loginResponse);
    }







}
