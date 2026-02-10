package com.motaamneh.falafel.service;

import com.motaamneh.falafel.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // User operations
    Optional<User> findUserById(Integer id);
    Optional<User> findUserByEmail(String email);
    User updateUser(Integer id, String fullName, String phone);

    // Admin operations on users
    List<User> getAllUsers();
    List<User> getUsersByStatus(Boolean isEnabled);
    void enableUser(Integer userId);
    void disableUser(Integer userId);

    // Utility
    boolean emailExists(String email);
}
