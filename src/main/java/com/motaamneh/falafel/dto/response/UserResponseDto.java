package com.motaamneh.falafel.dto.response;

import java.time.LocalDateTime;

public record UserResponseDto(Integer id,
                              String email,
                              String fullName,
                              String phone,
                              String role,
                              Boolean isEnabled,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
}
