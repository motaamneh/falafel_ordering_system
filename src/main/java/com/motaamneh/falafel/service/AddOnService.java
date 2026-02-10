package com.motaamneh.falafel.service;

import com.motaamneh.falafel.entity.AddOn;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AddOnService {
    // Restaurant operations - CRUD
    AddOn createAddOn(Integer restaurantId, String name, BigDecimal price);
    AddOn updateAddOn(Integer addOnId, String name, BigDecimal price, Boolean isAvailable);
    void deleteAddOn(Integer addOnId);

    // Query operations
    Optional<AddOn> findAddOnById(Integer id);
    List<AddOn> getRestaurantAddOns(Integer restaurantId);
    List<AddOn> getAvailableAddOns(Integer restaurantId);

    // Status management
    void markAsAvailable(Integer addOnId);
    void markAsUnavailable(Integer addOnId);
}
