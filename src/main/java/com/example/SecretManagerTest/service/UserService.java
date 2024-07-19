package com.example.SecretManagerTest.service;

import com.example.SecretManagerTest.dto.User;
import com.example.SecretManagerTest.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> getUserById(String id) {
        return userMapper.findById(id);
    }
}