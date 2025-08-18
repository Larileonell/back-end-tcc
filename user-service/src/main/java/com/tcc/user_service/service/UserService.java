package com.tcc.user_service.service;

import com.tcc.user_service.model.User;
import com.tcc.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final   UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> findAll() {
        return userRepository.findAll();
    };
    public User save(User user) {
        return userRepository.save(user);
    }



}
