package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Application;
import com.example.spring_telecom.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Get all applications
    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    // Get application by ID
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new application - date will be set automatically
    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        // No need to set date manually - it's handled by the entity
        Application savedApplication = applicationService.saveApplication(application);
        return ResponseEntity.ok(savedApplication);
    }

    // Update application status
    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            Application updatedApplication = applicationService.updateApplicationStatus(id, status);
            return ResponseEntity.ok(updatedApplication);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Get applications by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getApplicationsByUserId(@PathVariable Long userId) {
        List<Application> applications = applicationService.getApplicationsByUserId(userId);
        return ResponseEntity.ok(applications);
    }

    // Get applications by service ID
    @GetMapping("/service/{serviceId}")
    public ResponseEntity<List<Application>> getApplicationsByServiceId(@PathVariable Long serviceId) {
        List<Application> applications = applicationService.getApplicationsByServiceId(serviceId);
        return ResponseEntity.ok(applications);
    }

    // Get applications by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Application>> getApplicationsByStatus(@PathVariable String status) {
        List<Application> applications = applicationService.getApplicationsByStatus(status);
        return ResponseEntity.ok(applications);
    }


}