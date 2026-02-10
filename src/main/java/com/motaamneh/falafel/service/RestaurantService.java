package com.motaamneh.falafel.service;

import com.motaamneh.falafel.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    // Admin operations - CRUD
    Restaurant createRestaurant(String name, String email, String password,
                                String address, String phone);
    Restaurant updateRestaurant(Integer id, String name, String address, String phone);
    void deleteRestaurant(Integer id);

    // Query operations
    Optional<Restaurant> findRestaurantById(Integer id);
    Optional<Restaurant> findRestaurantByEmail(String email);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> getRestaurantsByStatus(Boolean isActive);

    // Status management
    void activateRestaurant(Integer restaurantId);
    void deactivateRestaurant(Integer restaurantId);

    // Utility
    boolean emailExists(String email);
}
