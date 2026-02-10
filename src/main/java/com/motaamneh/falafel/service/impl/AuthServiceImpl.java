package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.entity.User;
import com.motaamneh.falafel.exception.EmailAlreadyExistsException;
import com.motaamneh.falafel.exception.RestaurantDisabledException;
import com.motaamneh.falafel.exception.UserDisabledException;
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

    public AuthServiceImpl(UserRepository userRepository,
                           RestaurantRepository restaurantRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User registerUser(String email, String password, String fullName, String phone) {
        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email already registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setRole(Role.USER);
        user.setIsEnabled(true);

        return userRepository.save(user);

    }

    @Override
    public Optional<User> loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if(userOpt.isEmpty()) return Optional.empty();

        User user = userOpt.get();
        if(!user.getIsEnabled()){
            throw new UserDisabledException("Account is disabled");
        }
        if(!passwordEncoder.matches(password, user.getPassword())){
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<Restaurant> loginRestaurant(String email, String password) {
        Optional<Restaurant> restaurantOpt = restaurantRepository.findByEmail(email);
        if(restaurantOpt.isEmpty()) return Optional.empty();

        Restaurant restaurant = restaurantOpt.get();

        if(!restaurant.getIsActive()){
            throw new RestaurantDisabledException("Restaurant is disabled");
        }

        if(!passwordEncoder.matches(password, restaurant.getPassword())){
            return Optional.empty();
        }

        return Optional.of(restaurant);
    }
}
