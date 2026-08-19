package com.skillforge.skillforge_monolith.controller;

import com.skillforge.skillforge_monolith.dto.request.UserRegistrationRequest;
import com.skillforge.skillforge_monolith.dto.response.UserResponse;
import com.skillforge.skillforge_monolith.entity.User;
import com.skillforge.skillforge_monolith.mapper.UserMapper;
import com.skillforge.skillforge_monolith.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@CrossOrigin()
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        User user = userService.createUser(request.getEmail(), request.getPassword(), request.getName());
        return userMapper.toResponse(user);
    }

    @GetMapping()
    public ResponseEntity<UserResponse> getById() {
        Long userId = getCurrentUser().getId();
        return userService.findById(userId)
                .map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
