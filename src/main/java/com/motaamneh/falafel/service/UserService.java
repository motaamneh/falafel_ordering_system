package com.motaamneh.falafel.service;

import com.motaamneh.falafel.dto.request.UserUpdateRequestDto;
import com.motaamneh.falafel.dto.response.UserResponseDto;
import com.motaamneh.falafel.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // User operations
    UserResponseDto findUserById(Integer id);
    UserResponseDto findUserByEmail(String email);
    UserResponseDto updateUser(Integer id, UserUpdateRequestDto dto);

    // Admin operations on users
    List<UserResponseDto> getAllUsers();
    List<UserResponseDto> getUsersByStatus(Boolean isEnabled);
    void enableUser(Integer userId);
    void disableUser(Integer userId);

    // Utility
    boolean emailExists(String email);
}
