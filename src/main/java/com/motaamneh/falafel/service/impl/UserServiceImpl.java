package com.motaamneh.falafel.service.impl;

import com.motaamneh.falafel.repository.UserRepository;
import com.motaamneh.falafel.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


}
