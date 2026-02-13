package com.motaamneh.falafel.mapper;

import com.motaamneh.falafel.dto.response.AddOnPublicDto;
import com.motaamneh.falafel.dto.response.AddOnResponseDto;
import com.motaamneh.falafel.entity.AddOn;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AddOnMapper {
    public AddOnResponseDto toResponseDto(AddOn addOn) {
        return new AddOnResponseDto(
                addOn.getId(),
                addOn.getRestaurant().getId(),
                addOn.getName(),
                addOn.getPrice(),
                addOn.getIsAvailable(),
                addOn.getCreatedAt(),
                addOn.getUpdatedAt()
        );
    }

    public List<AddOnResponseDto> toResponseDtoList(List<AddOn> addOns) {
        return addOns.stream().map(this::toResponseDto).toList();
    }

    public AddOnPublicDto toPublicDto(AddOn addOn) {
        return new AddOnPublicDto(
                addOn.getId(),
                addOn.getName(),
                addOn.getPrice(),
                addOn.getIsAvailable()
        );
    }
    public List<AddOnPublicDto> toPublicDtoList(List<AddOn> addOns) {
        return addOns.stream().map(this::toPublicDto).toList();

    }


}
