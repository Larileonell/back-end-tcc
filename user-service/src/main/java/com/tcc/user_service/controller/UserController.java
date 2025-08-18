package com.tcc.user_service.controller;

import com.tcc.user_service.model.User;
import com.tcc.user_service.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }
    @PostMapping
    public User create(@RequestBody User user) {
        return userService.save(user);
    }
}
