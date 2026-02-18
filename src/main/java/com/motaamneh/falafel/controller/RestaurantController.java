package com.motaamneh.falafel.controller;


import com.motaamneh.falafel.dto.request.RestaurantCreateRequestDto;
import com.motaamneh.falafel.dto.request.RestaurantUpdateRequestDto;
import com.motaamneh.falafel.dto.response.RestaurantPublicDto;
import com.motaamneh.falafel.dto.response.RestaurantResponseDto;
import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantResponseDto> createRestaurant(@RequestBody RestaurantCreateRequestDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(dto));

    }

    @GetMapping("/allRestaurants")
    public ResponseEntity<List<RestaurantPublicDto>> getActiveRestaurants() {
        return ResponseEntity.ok(restaurantService.getRestaurantsByStatus(true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> getRestaurantById(@PathVariable Integer id) {
        return ResponseEntity.ok(restaurantService.findRestaurantById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantResponseDto> updateRestaurant(@PathVariable Integer id,@RequestBody RestaurantUpdateRequestDto restaurantUpdateRequestDto) {
        return ResponseEntity.ok(restaurantService.updateRestaurant(id,restaurantUpdateRequestDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Integer id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activateRestaurant(@PathVariable Integer id) {
        restaurantService.activateRestaurant(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deactivateRestaurant(@PathVariable Integer id) {
        restaurantService.deactivateRestaurant(id);
        return ResponseEntity.noContent().build();

    }







}
