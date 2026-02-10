package com.motaamneh.falafel.repository;


import com.motaamneh.falafel.entity.AddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddOnRepository extends JpaRepository<AddOn,Integer> {

    // Restaurant manages their own add-ons
    List<AddOn> findByRestaurantId(Integer restaurantId);

    // Find available add-ons for a restaurant (when user is ordering)
    List<AddOn> findByRestaurantIdAndIsAvailable(Integer restaurantId, Boolean isAvailable);

}
