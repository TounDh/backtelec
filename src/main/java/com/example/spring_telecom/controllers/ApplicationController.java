package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Application;
import com.example.spring_telecom.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Get applications by user ID



    @GetMapping
    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationService.getAllApplications();
        return ResponseEntity.ok(applications);
    }

    // Get applications by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Application>> getApplicationsByUserId(@PathVariable Long userId) {
        List<Application> applications = applicationService.getApplicationsByUserId(userId);
        return ResponseEntity.ok(applications);
    }




    // Update application status
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        // Validate status
        if (!"APPROVED".equals(status) && !"REJECTED".equals(status)) {
            return ResponseEntity.badRequest().body("Invalid status. Must be 'APPROVED' or 'REJECTED'");
        }

        try {
            applicationService.updateApplicationStatus(id, status);
            return ResponseEntity.ok("Application status updated to " + status);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}