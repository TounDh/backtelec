package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Role;
import com.example.spring_telecom.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository repository;

    public List<Role> getAll() {
        return repository.findAll();
    }

    public Role getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Role create(Role role) {
        if (role.getId() != null && role.getId() == 0) {
            role.setId(null);
        }
        return repository.save(role);
    }

    public Role update(Long id, Role role) {
        Role existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setRole(role.isRole());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}