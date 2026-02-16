package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.dto.request.AddOnCreateRequestDto;
import com.motaamneh.falafel.dto.request.AddOnUpdateRequestDto;
import com.motaamneh.falafel.dto.response.AddOnPublicDto;
import com.motaamneh.falafel.dto.response.AddOnResponseDto;
import com.motaamneh.falafel.entity.AddOn;
import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.exception.AddOnNotFoundException;
import com.motaamneh.falafel.exception.InvalidPriceException;
import com.motaamneh.falafel.exception.RestaurantNotFoundException;
import com.motaamneh.falafel.mapper.AddOnMapper;
import com.motaamneh.falafel.repository.AddOnRepository;
import com.motaamneh.falafel.repository.RestaurantRepository;
import com.motaamneh.falafel.service.AddOnService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddOnServiceImpl implements AddOnService {

    private final AddOnRepository addOnRepository;
    private final RestaurantRepository restaurantRepository;
    private final AddOnMapper addOnMapper;

    public AddOnServiceImpl(AddOnRepository addOnRepository, RestaurantRepository restaurantRepository, AddOnMapper addOnMapper) {
        this.addOnRepository = addOnRepository;
        this.restaurantRepository = restaurantRepository;
        this.addOnMapper = addOnMapper;
    }

    @Override
    public AddOnResponseDto createAddOn(Integer restaurantId, AddOnCreateRequestDto
            dto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
        if (dto.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException("Price must be positive");
        }
        AddOn addOn = new AddOn();
        addOn.setRestaurant(restaurant);
        addOn.setName(dto.name());
        addOn.setPrice(dto.price());
        addOn.setIsAvailable(true);

        return addOnMapper.toResponseDto(addOnRepository.save(addOn));
    }

    @Override
    public AddOnResponseDto updateAddOn(Integer addOnId, AddOnUpdateRequestDto dto) {
        AddOn addOn = addOnRepository.findById(addOnId)
                .orElseThrow(() -> new AddOnNotFoundException("Add-on not found"));
        if (dto.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException("Price must be positive");
        }
        addOn.setName(dto.name());
        addOn.setPrice(dto.price());
        addOn.setIsAvailable(dto.isAvailable());

        return addOnMapper.toResponseDto(addOnRepository.save(addOn));
    }

    @Override
    public void deleteAddOn(Integer addOnId) {
        AddOn addOn = addOnRepository.findById(addOnId)
                .orElseThrow(() -> new AddOnNotFoundException("Add-on not found"));
        addOnRepository.delete(addOn);
    }

    @Override
    public AddOnResponseDto findAddOnById(Integer id) {
        AddOn addOn = addOnRepository.findById(id)
                .orElseThrow(() -> new AddOnNotFoundException("Add-on not found"));
        return addOnMapper.toResponseDto(addOn);
    }

    @Override
    public List<AddOnResponseDto> getRestaurantAddOns(Integer restaurantId) {
        return
                addOnMapper.toResponseDtoList(addOnRepository.findByRestaurantId(restaurantId));
    }

    @Override
    public List<AddOnPublicDto> getAvailableAddOns(Integer restaurantId) {
        return addOnMapper.toPublicDtoList(
                addOnRepository.findByRestaurantIdAndIsAvailable(restaurantId, true));
    }

    @Override
    public void markAsAvailable(Integer addOnId) {
        AddOn addOn = addOnRepository.findById(addOnId)
                .orElseThrow(() -> new AddOnNotFoundException("Add-on not found"));
        addOn.setIsAvailable(true);
        addOnRepository.save(addOn);
    }

    @Override
    public void markAsUnavailable(Integer addOnId) {
        AddOn addOn = addOnRepository.findById(addOnId)
                .orElseThrow(() -> new AddOnNotFoundException("Add-on not found"));
        addOn.setIsAvailable(false);
        addOnRepository.save(addOn);
    }
}
