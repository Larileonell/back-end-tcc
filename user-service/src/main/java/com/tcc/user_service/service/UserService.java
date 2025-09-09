package com.tcc.user_service.service;

import com.tcc.user_service.model.User;
import com.tcc.user_service.repository.UserRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Counter loginsCounter;
    private final Counter registersCounter;

    public UserService(UserRepository userRepository, MeterRegistry registry) {
        this.userRepository = userRepository;
        this.loginsCounter = registry.counter("user.logins.total");
        this.registersCounter = registry.counter("user.registers.total");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        registersCounter.increment();
        return userRepository.save(user);
    }

    public void incrementLogins() {
        loginsCounter.increment();
    }
}
