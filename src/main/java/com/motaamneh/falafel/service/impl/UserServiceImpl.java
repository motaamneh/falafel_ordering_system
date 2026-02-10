package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.entity.User;
import com.motaamneh.falafel.exception.UserNotFoundException;
import com.motaamneh.falafel.repository.UserRepository;
import com.motaamneh.falafel.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public Optional<User> findUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    @Override
    public User updateUser(Integer id, String fullName, String phone) {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User not found"));
        user.setFullName(fullName);
        user.setPhone(phone);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByStatus(Boolean isEnabled) {
        return userRepository.findByIsEnabled(isEnabled);
    }

    @Override
    public void enableUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));
        user.setIsEnabled(true);

        userRepository.save(user);
    }

    @Override
    public void disableUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));
        user.setIsEnabled(false);

        userRepository.save(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
