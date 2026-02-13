package com.motaamneh.falafel.mapper;

import com.motaamneh.falafel.dto.request.RestaurantLoginRequestDto;
import com.motaamneh.falafel.dto.response.LoginResponseDto;
import com.motaamneh.falafel.dto.response.RestaurantPublicDto;
import com.motaamneh.falafel.dto.response.RestaurantResponseDto;
import com.motaamneh.falafel.entity.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantMapper {

    public RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant) {
        return new RestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getEmail(),
                restaurant.getAddress(),
                restaurant.getPhone(),
                restaurant.getIsActive(),
                restaurant.getCreatedAt(),
                restaurant.getUpdatedAt()
        );
    }
    public RestaurantPublicDto toPublicDto(Restaurant restaurant){
        return new RestaurantPublicDto(
          restaurant.getId(),
          restaurant.getName(),
          restaurant.getAddress(),
          restaurant.getPhone(),
          restaurant.getIsActive()
        );
    }
    public LoginResponseDto toRestaurantLoginRequestDto(Restaurant restaurant){
        return new LoginResponseDto(
          restaurant.getId(),
          restaurant.getEmail(),
          restaurant.getName(),
          "RESTAURANT",
          "Login successful"
        );
    }
    public List<RestaurantResponseDto> toResponseDto(List<Restaurant> restaurants){
        return restaurants.stream().map(this::toRestaurantResponseDto).toList();
    }
    public List<RestaurantPublicDto> toPublicDtoList(List<Restaurant> restaurants){
        return restaurants.stream().map(this::toPublicDto).toList();
    }


}
