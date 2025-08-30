package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Application;
import com.example.spring_telecom.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository repository;

    public List<Application> getAll() {
        return repository.findAll();
    }

    public Application getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Application create(Application application) {
        if (application.getId() != null && application.getId() == 0) {
            application.setId(null);
        }
        return repository.save(application);
    }

    public Application update(Long id, Application application) {
        Application existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setSubmissionDate(application.getSubmissionDate());
            existing.setResponseDate(application.getResponseDate());
            existing.setResponse(application.isResponse());
            existing.setStatus(application.getStatus());
            existing.setUser(application.getUser());
            existing.setSrvce(application.getSrvce());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}