package com.motaamneh.falafel.service;

import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.entity.User;

import java.util.Optional;

public interface AuthService {
    // User authentication
    User registerUser(String email, String password, String fullName, String phone);
    Optional<User> loginUser(String email, String password);

    // Restaurant authentication
    Optional<Restaurant> loginRestaurant(String email, String password);
}
