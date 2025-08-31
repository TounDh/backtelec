package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Address;
import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.services.AddressService;
import com.example.spring_telecom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;



    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAddressByUserId(@PathVariable Long userId) {
        try {
            Optional<User> user = userService.findById(userId);

            if (user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "No user found with ID: " + userId));
            }

            Optional<Address> address = addressService.getAddressByUser(user.get());
            if (address.isPresent()) {
                return ResponseEntity.ok(Map.of("success", true, "data", address.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "No address found for user ID: " + userId));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error retrieving address: " + e.getMessage()));
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> saveOrUpdateAddress(@PathVariable Long userId, @RequestBody Address address) {
        try {
            Optional<User> user = userService.findById(userId);

            if (user.isPresent()) {
                Address savedAddress = addressService.saveOrUpdateAddress(address, user.get());
                return ResponseEntity.ok(Map.of("success", true, "data", savedAddress));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", "User not found with ID: " + userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error saving address: " + e.getMessage()));
        }
    }



    // DELETE address by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting address: " + e.getMessage());
        }
    }

    // Fallback for incorrect endpoints
    @GetMapping
    public ResponseEntity<?> handleRootGet() {
        return ResponseEntity.badRequest().body("Please use /api/address/user/{userId} endpoint");
    }

    @PostMapping
    public ResponseEntity<?> handleRootPost() {
        return ResponseEntity.badRequest().body("Please use /api/address/user/{userId} endpoint with user ID");
    }
}