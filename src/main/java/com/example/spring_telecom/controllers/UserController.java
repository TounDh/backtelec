package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.services.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        if (service.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        User savedUser = service.create(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = service.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (ConstraintViolationException e) {
            Map<String, String> errors = new HashMap<>();
            e.getConstraintViolations().forEach(violation ->
                    errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
            return ResponseEntity.badRequest().body(errors);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(@RequestParam String email) {
        User user = service.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("=== USER DEBUG INFO ===");
        System.out.println("User ID: " + user.getId());
        System.out.println("Phone: " + user.getPhone());
        System.out.println("Birthdate: " + user.getBirthdate());
        System.out.println("All fields: " + user.toString());
        System.out.println("=========================");

        return ResponseEntity.ok(user);
    }
}