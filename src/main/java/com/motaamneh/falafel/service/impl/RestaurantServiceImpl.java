package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.exception.EmailAlreadyExistsException;
import com.motaamneh.falafel.exception.RestaurantNotFoundException;
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

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, PasswordEncoder passwordEncoder) {
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Restaurant createRestaurant(String name, String email, String password, String address, String phone) {
        if(restaurantRepository.existsByEmail(email)){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setEmail(email);
        restaurant.setPassword(passwordEncoder.encode(password));
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        restaurant.setIsActive(true);


        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Integer id, String name, String address, String phone) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found"));

        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);


        return restaurantRepository.save(restaurant);

    }

    @Override
    public void deleteRestaurant(Integer id) {
        if(!restaurantRepository.existsById(id)){
            throw new RestaurantNotFoundException("Restaurant not found");
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public Optional<Restaurant> findRestaurantById(Integer id) {
        return restaurantRepository.findById(id);
    }

    @Override
    public Optional<Restaurant> findRestaurantByEmail(String email) {
        return restaurantRepository.findByEmail(email);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getRestaurantsByStatus(Boolean isActive) {
        return restaurantRepository.findByIsActive(isActive);
    }

    @Override
    public void activateRestaurant(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found"));
        restaurant.setIsActive(true);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void deactivateRestaurant(Integer restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found"));
        restaurant.setIsActive(false);
        restaurantRepository.save(restaurant);
    }

    @Override
    public boolean emailExists(String email) {
        return restaurantRepository.existsByEmail(email);
    }
}
