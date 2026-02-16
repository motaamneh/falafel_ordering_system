package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.dto.request.RestaurantCreateRequestDto;
import com.motaamneh.falafel.dto.request.RestaurantUpdateRequestDto;
import com.motaamneh.falafel.dto.response.RestaurantPublicDto;
import com.motaamneh.falafel.dto.response.RestaurantResponseDto;
import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.exception.EmailAlreadyExistsException;
import com.motaamneh.falafel.exception.RestaurantNotFoundException;
import com.motaamneh.falafel.mapper.RestaurantMapper;
import com.motaamneh.falafel.repository.RestaurantRepository;
import com.motaamneh.falafel.service.RestaurantService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestaurantMapper restaurantMapper;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, PasswordEncoder passwordEncoder, RestaurantMapper restaurantMapper) {
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
        this.restaurantMapper = restaurantMapper;
    }
    @Override
    public RestaurantResponseDto createRestaurant(RestaurantCreateRequestDto dto) {
        if (restaurantRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(dto.name());
        restaurant.setEmail(dto.email());
        restaurant.setPassword(passwordEncoder.encode(dto.password()));
        restaurant.setAddress(dto.address());
        restaurant.setPhone(dto.phone());
        restaurant.setIsActive(true);

        return
                restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));
    }

    @Override
    public RestaurantResponseDto updateRestaurant(Integer id,
                                                  RestaurantUpdateRequestDto dto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
                        restaurant.setName(dto.name());
        restaurant.setAddress(dto.address());
        restaurant.setPhone(dto.phone());

        return
                restaurantMapper.toRestaurantResponseDto(restaurantRepository.save(restaurant));
    }

    @Override
    public void deleteRestaurant(Integer id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public RestaurantResponseDto findRestaurantById(Integer id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        return restaurantMapper.toRestaurantResponseDto(restaurant);
    }

    @Override
    public RestaurantResponseDto findRestaurantByEmail(String email) {
        Restaurant restaurant = restaurantRepository.findByEmail(email)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        return restaurantMapper.toRestaurantResponseDto(restaurant);
    }

    @Override
    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantMapper.toResponseDto(restaurantRepository.findAll());
    }

    @Override
    public List<RestaurantPublicDto> getRestaurantsByStatus(Boolean isActive) {
        return
                restaurantMapper.toPublicDtoList(restaurantRepository.findByIsActive(isActive));
    }

    @Override
    public void activateRestaurant(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
                        restaurant.setIsActive(true);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void deactivateRestaurant(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
                        restaurant.setIsActive(false);
        restaurantRepository.save(restaurant);
    }

    @Override
    public boolean emailExists(String email) {
        return restaurantRepository.existsByEmail(email);
    }
}
