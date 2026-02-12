package com.motaamneh.falafel.dto.response;

import java.math.BigDecimal;

public record AddOnPublicDto(
        Integer id,
        String name,
        BigDecimal price,
        Boolean isAvailable
) {
}
