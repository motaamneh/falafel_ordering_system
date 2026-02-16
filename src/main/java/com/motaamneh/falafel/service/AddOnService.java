package com.motaamneh.falafel.service;

import com.motaamneh.falafel.dto.request.AddOnCreateRequestDto;
import com.motaamneh.falafel.dto.request.AddOnUpdateRequestDto;
import com.motaamneh.falafel.dto.response.AddOnPublicDto;
import com.motaamneh.falafel.dto.response.AddOnResponseDto;
import com.motaamneh.falafel.entity.AddOn;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AddOnService {
    // Restaurant operations - CRUD
    AddOnResponseDto createAddOn(Integer restaurantId, AddOnCreateRequestDto dto);
    AddOnResponseDto updateAddOn(Integer addOnId, AddOnUpdateRequestDto dto);

    // Query operations
    AddOnResponseDto findAddOnById(Integer id);
    List<AddOnResponseDto> getRestaurantAddOns(Integer restaurantId);
    List<AddOnPublicDto> getAvailableAddOns(Integer restaurantId);

    // Status management
    void markAsAvailable(Integer addOnId);
    void markAsUnavailable(Integer addOnId);
    void deleteAddOn(Integer addOnId);


}
