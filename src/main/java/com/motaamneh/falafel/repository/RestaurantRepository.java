package com.motaamneh.falafel.repository;


import com.motaamneh.falafel.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    // For restaurant login
    Optional<Restaurant> findByEmail(String email);

    // For admin - filter restaurants by active status
    List<Restaurant> findByIsActive(Boolean isActive);

    // Check if email already exists (when admin creates restaurant)
    boolean existsByEmail(String email);

}
