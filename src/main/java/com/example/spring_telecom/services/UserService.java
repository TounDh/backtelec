package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.User;
import com.example.spring_telecom.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

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
}