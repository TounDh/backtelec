package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Find user by email
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        User user = userOptional.get();

        // Check password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }

        // Create response with user data (excluding password)
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("user", Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "surname", user.getSurname(),
                "email", user.getEmail(),
                "role", user.getRole()
        ));
        response.put("token", "jwt-token-placeholder"); // Implement JWT if needed

        return ResponseEntity.ok(response);
    }

    // Add this method to your UserService
    // public Optional<User> findByEmail(String email) {
    //     return userRepository.findByEmail(email);
    // }
}