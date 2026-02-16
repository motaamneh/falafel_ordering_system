package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.dto.request.RestaurantLoginRequestDto;
import com.motaamneh.falafel.dto.request.UserLoginRequestDto;
import com.motaamneh.falafel.dto.request.UserRegistrationDto;
import com.motaamneh.falafel.dto.response.LoginResponseDto;
import com.motaamneh.falafel.dto.response.RestaurantResponseDto;
import com.motaamneh.falafel.dto.response.UserResponseDto;
import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.entity.User;
import com.motaamneh.falafel.exception.EmailAlreadyExistsException;
import com.motaamneh.falafel.exception.InvalidCredentialsException;
import com.motaamneh.falafel.exception.RestaurantDisabledException;
import com.motaamneh.falafel.exception.UserDisabledException;
import com.motaamneh.falafel.mapper.RestaurantMapper;
import com.motaamneh.falafel.mapper.UserMapper;
import com.motaamneh.falafel.model.Role;
import com.motaamneh.falafel.repository.RestaurantRepository;
import com.motaamneh.falafel.repository.UserRepository;
import com.motaamneh.falafel.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RestaurantMapper restaurantMapper;

    public AuthServiceImpl(UserRepository userRepository, RestaurantRepository restaurantRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, RestaurantMapper restaurantMapper) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public UserResponseDto registerUser(UserRegistrationDto registrationDto) {
        if(userRepository.existsByEmail(registrationDto.email())){
            throw new EmailAlreadyExistsException("Email already registered");
        }
        User user = new User();
        user.setEmail(registrationDto.email());
        user.setPassword(passwordEncoder.encode(registrationDto.password()));
        user.setFullName(registrationDto.fullName());
        user.setPhone(registrationDto.phone());
        user.setRole(Role.USER);
        user.setIsEnabled(true);

        return userMapper.toResponseDto(userRepository.save(user));

    }

    @Override
    public LoginResponseDto loginUser(UserLoginRequestDto loginRequestDto) {
        Optional<User> userOpt = userRepository.findByEmail(loginRequestDto.email());

        if(userOpt.isEmpty()) throw new InvalidCredentialsException("Invalid email or password");

        User user = userOpt.get();
        if(!user.getIsEnabled()){
            throw new UserDisabledException("Account is disabled");
        }
        if(!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }
        //UserResponseDto userResponseDto = userMapper.toResponseDto(user);
        return userMapper.toLoginResponseDto(user);
    }

    @Override
    public LoginResponseDto loginRestaurant(RestaurantLoginRequestDto restaurantLoginRequestDto) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findByEmail(restaurantLoginRequestDto.email());
        if(restaurantOpt.isEmpty()) throw new InvalidCredentialsException("Invalid email or password");

        Restaurant restaurant = restaurantOpt.get();

        if(!restaurant.getIsActive()){
            throw new RestaurantDisabledException("Restaurant is disabled");
        }

        if(!passwordEncoder.matches(restaurantLoginRequestDto.password(), restaurant.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        //RestaurantResponseDto restaurantResponseDto = restaurantMapper.toRestaurantResponseDto(restaurant);
        return restaurantMapper.toRestaurantLoginRequestDto(restaurant);
    }
}
