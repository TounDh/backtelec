package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.TelecomUser; // Add this import
import com.example.spring_telecom.repositories.TelecomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelecomUserService {
    @Autowired
    private TelecomUserRepository repository;

    public List<TelecomUser> getAll() {
        return repository.findAll();
    }

    public TelecomUser getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public TelecomUser create(TelecomUser user) {
        if (user.getId() != null && user.getId() == 0) {
            user.setId(null); // Reset id to null for new entities
        }
        return repository.save(user);
    }

    public TelecomUser update(Long id, TelecomUser user) {
        TelecomUser existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(user.getName());
            existing.setPhone(user.getPhone());
            existing.setEmail(user.getEmail());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}