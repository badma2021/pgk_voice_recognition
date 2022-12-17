package com.example.pgk.service;

import com.example.pgk.dao.UserRepository;
import com.example.pgk.model.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserRepository userRepository;

    public Optional<User> findUserByEmail(String email) {

        return userRepository.getUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
