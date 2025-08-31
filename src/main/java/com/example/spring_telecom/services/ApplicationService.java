package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Application;
import com.example.spring_telecom.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<Application> getApplicationById(Long id) {
        return applicationRepository.findById(id);
    }

    public Application saveApplication(Application application) {
        // The application date will be set automatically by the entity's @PrePersist
        return applicationRepository.save(application);
    }

    public Application updateApplicationStatus(Long id, String status) {
        return applicationRepository.findById(id)
                .map(application -> {
                    application.setStatus(status);
                    return applicationRepository.save(application);
                })
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
    }

    public List<Application> getApplicationsByUserId(Long userId) {
        return applicationRepository.findByUserId(userId);
    }

    public List<Application> getApplicationsByServiceId(Long serviceId) {
        return applicationRepository.findBySrvceId(serviceId);
    }

    // Get applications by status
    public List<Application> getApplicationsByStatus(String status) {
        return applicationRepository.findByStatus(status);
    }

    // Get applications by date range
    public List<Application> getApplicationsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return applicationRepository.findByApplicationDateBetween(startDate, endDate);
    }
}