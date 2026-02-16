package com.motaamneh.falafel.service;

import com.motaamneh.falafel.dto.request.RestaurantCreateRequestDto;
import com.motaamneh.falafel.dto.request.RestaurantUpdateRequestDto;
import com.motaamneh.falafel.dto.response.RestaurantPublicDto;
import com.motaamneh.falafel.dto.response.RestaurantResponseDto;
import com.motaamneh.falafel.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    // Admin operations - CRUD
    RestaurantResponseDto createRestaurant(RestaurantCreateRequestDto dto);
    RestaurantResponseDto updateRestaurant(Integer id, RestaurantUpdateRequestDto dto);
    void deleteRestaurant(Integer id);

    // Query operations
    RestaurantResponseDto findRestaurantById(Integer id);
    RestaurantResponseDto findRestaurantByEmail(String email);
    List<RestaurantResponseDto> getAllRestaurants();
    List<RestaurantPublicDto> getRestaurantsByStatus(Boolean isActive);

    // Status management
    void activateRestaurant(Integer restaurantId);
    void deactivateRestaurant(Integer restaurantId);

    // Utility
    boolean emailExists(String email);
}
