package com.motaamneh.falafel.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponseDto(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path,
        List<FieldErrorDto> fieldErrors
) {
}
