package com.tcc.user_service.controller;

import com.tcc.user_service.model.User;
import com.tcc.user_service.repository.UserRepository;
import com.tcc.user_service.security.JwtUtil;
import com.tcc.user_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, UserService userService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userService.save(user);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        User found = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (passwordEncoder.matches(user.getPassword(), found.getPassword())) {
            userService.incrementLogins();
            String token = jwtUtil.generateToken(found.getId());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Senha inválida"));
        }
    }
}