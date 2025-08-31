package com.example.spring_telecom.controllers;
import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService service;


    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }






    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        // Check for duplicate email (optional)
        if (service.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User savedUser = service.create(user);
        return ResponseEntity.ok(savedUser);
    }


    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


    @GetMapping("/details")
    public User getUserDetails(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        User user = service.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // DEBUG: Check if fields are populated
        System.out.println("=== USER DEBUG INFO ===");
        System.out.println("User ID: " + user.getId());
        System.out.println("Phone: " + user.getPhone());
        System.out.println("Birthdate: " + user.getBirthdate());
        System.out.println("All fields: " + user.toString());
        System.out.println("=========================");

        return user;
    }

}