package com.skillforge.skillforge_monolith.controller;


import com.skillforge.skillforge_monolith.dto.request.UserRegistrationRequest;
import com.skillforge.skillforge_monolith.dto.response.UserResponse;
import com.skillforge.skillforge_monolith.entity.User;
import com.skillforge.skillforge_monolith.mapper.UserMapper;
import com.skillforge.skillforge_monolith.security.JwtUtil;
import com.skillforge.skillforge_monolith.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private  UserService userService;
    private  UserMapper userMapper;
    private  PasswordEncoder passwordEncoder;
    private  JwtUtil jwtUtil;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User user = userService.createUser(request.getEmail(), hashedPassword, request.getName());
        return userMapper.toResponse(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(""+user.getId(), user.getEmail());
        return ResponseEntity.ok(Map.of(
                "token", token,
                "userId", user.getId().toString()
        ));
    }
}