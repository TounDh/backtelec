package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Role;
import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.repositories.RoleRepository;
import com.example.spring_telecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;



    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getById(Long id) {
        return repository.findById(id).orElse(null);
    }



    public User update(Long id, User user) {
        User existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(user.getName());
            existing.setSurname(user.getSurname());
            existing.setEmail(user.getEmail());
            existing.setPhone(user.getPhone());
            existing.setBirthdate(user.getBirthdate());
            existing.setPassword(user.getPassword());
            existing.setRole(user.getRole());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }



    public User create(User user) {
        System.out.println("=== USER CREATION DEBUG ===");
        System.out.println("Phone: " + user.getPhone());
        System.out.println("Birthdate: " + user.getBirthdate());
        System.out.println("===========================");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // AUTO-ASSIGN ROLE_ID = 2
        Role userRole = new Role();
        userRole.setId(2L); // Set role_id to 2 directly
        user.setRole(userRole);

        return repository.save(user);
    }
    // Add this method to your UserService class
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}