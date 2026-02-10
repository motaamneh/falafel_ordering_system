package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.entity.User;
import com.motaamneh.falafel.exception.EmailAlreadyExistsException;
import com.motaamneh.falafel.model.Role;
import com.motaamneh.falafel.repository.RestaurantRepository;
import com.motaamneh.falafel.repository.UserRepository;
import com.motaamneh.falafel.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           RestaurantRepository restaurantRepository){
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;

    }


    @Override
    public User registerUser(String email, String password, String fullName, String phone) {
        if(userRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email already registered");
        }
        User user = new User();
        user.setEmail(email);
        user.setPassword((password));
        user.setFullName(fullName);
        user.setPhone(phone);
        user.setRole(Role.USER);
        user.setIsEnabled(true);

        return userRepository.save(user);

    }

    @Override
    public Optional<User> loginUser(String email, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<Restaurant> loginRestaurant(String email, String password) {
        return Optional.empty();
    }
}
