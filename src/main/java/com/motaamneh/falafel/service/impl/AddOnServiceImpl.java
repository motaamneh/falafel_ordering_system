package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.entity.AddOn;
import com.motaamneh.falafel.entity.Restaurant;
import com.motaamneh.falafel.exception.AddOnNotFoundException;
import com.motaamneh.falafel.exception.InvalidPriceException;
import com.motaamneh.falafel.exception.RestaurantNotFoundException;
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

    public AddOnServiceImpl(AddOnRepository addOnRepository, RestaurantRepository restaurantRepository) {
        this.addOnRepository = addOnRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public AddOn createAddOn(Integer restaurantId, String name, BigDecimal price) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new RestaurantNotFoundException("Restaurant not found"));
        if(price.compareTo(BigDecimal.ZERO)<=0){
            throw new InvalidPriceException("Price must be positive");
        }
        AddOn addOn= new AddOn();
        addOn.setRestaurant(restaurant);
        addOn.setName(name);
        addOn.setPrice(price);
        addOn.setIsAvailable(true);

        return addOnRepository.save(addOn);

    }

    @Override
    public AddOn updateAddOn(Integer addOnId, String name, BigDecimal price, Boolean isAvailable) {
        AddOn addOn= addOnRepository.findById(addOnId)
                .orElseThrow(()-> new AddOnNotFoundException("Add-on not found"));

        if(price.compareTo(BigDecimal.ZERO)<=0){
            throw new InvalidPriceException("Price must be positive");
        }

        addOn.setName(name);
        addOn.setPrice(price);
        addOn.setIsAvailable(isAvailable);

        return addOnRepository.save(addOn);

    }

    @Override
    public void deleteAddOn(Integer addOnId) {
        AddOn addOn= addOnRepository.findById(addOnId)
                .orElseThrow(()-> new AddOnNotFoundException("Add-on not found"));

        addOnRepository.delete(addOn);
    }

    @Override
    public Optional<AddOn> findAddOnById(Integer id) {
        return  addOnRepository.findById(id);
    }

    @Override
    public List<AddOn> getRestaurantAddOns(Integer restaurantId) {
        return addOnRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public List<AddOn> getAvailableAddOns(Integer restaurantId) {
        return addOnRepository.findByRestaurantIdAndIsAvailable(restaurantId, true);
    }

    @Override
    public void markAsAvailable(Integer addOnId) {
        AddOn addOn= addOnRepository.findById(addOnId)
                .orElseThrow(()-> new AddOnNotFoundException("Add-on not found"));

        addOn.setIsAvailable(true);
        addOnRepository.save(addOn);
    }

    @Override
    public void markAsUnavailable(Integer addOnId) {
        AddOn addOn= addOnRepository.findById(addOnId)
                .orElseThrow(()-> new AddOnNotFoundException("Add-on not found"));

        addOn.setIsAvailable(false);
        addOnRepository.save(addOn);

    }
}
