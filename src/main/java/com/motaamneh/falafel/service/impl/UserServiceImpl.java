package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.dto.request.UserUpdateRequestDto;
import com.motaamneh.falafel.dto.response.UserResponseDto;
import com.motaamneh.falafel.entity.User;
import com.motaamneh.falafel.exception.UserNotFoundException;
import com.motaamneh.falafel.mapper.UserMapper;
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
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto findUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto updateUser(Integer id, UserUpdateRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setFullName(dto.fullName());
        user.setPhone(dto.phone());
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userMapper.toResponseDto(userRepository.findAll());
    }

    @Override
    public List<UserResponseDto> getUsersByStatus(Boolean isEnabled) {
        return userMapper.toResponseDto(userRepository.findByIsEnabled(isEnabled));
    }

    @Override
    public void enableUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setIsEnabled(false);
        userRepository.save(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
