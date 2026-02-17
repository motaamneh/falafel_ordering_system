package com.motaamneh.falafel.dto.response;

public record LoginResponseDto(
        Integer id,
        String email,
        String displayName,
        String role,
        String message,
        String token

) {
}
