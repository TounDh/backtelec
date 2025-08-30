package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Installation;
import com.example.spring_telecom.repositories.InstallationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstallationService {
    @Autowired
    private InstallationRepository repository;

    public List<Installation> getAll() {
        return repository.findAll();
    }

    public Installation getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Installation create(Installation installation) {
        if (installation.getId() != null && installation.getId() == 0) {
            installation.setId(null);
        }
        return repository.save(installation);
    }

    public Installation update(Long id, Installation installation) {
        Installation existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setDate(installation.getDate());
            existing.setStatus(installation.getStatus());
            existing.setApplication(installation.getApplication());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}