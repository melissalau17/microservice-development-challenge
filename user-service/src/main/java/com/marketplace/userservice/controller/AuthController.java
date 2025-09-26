package com.marketplace.userservice.controller;

import com.marketplace.userservice.dto.AuthResponse;
import com.marketplace.userservice.dto.LoginRequest;
import com.marketplace.userservice.model.User;
import com.marketplace.userservice.service.UserService;
import com.marketplace.userservice.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = (User) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt, user.getId(), user.getUsername(), user.getRole().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        if (userService.usernameExists(user.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User newUser = userService.createUser(user);
        String jwt = jwtUtil.generateToken(newUser.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt, newUser.getId(), newUser.getUsername(), newUser.getRole().name()));
    }
}