package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Installation;
import com.example.spring_telecom.repositories.InstallationRepository;
import com.example.spring_telecom.services.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/installations")
public class InstallationController {
    @Autowired
    private InstallationService service;
    @Autowired
    private InstallationRepository installationRepository;

    @GetMapping
    public List<Installation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Installation getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Installation create(@RequestBody Installation installation) {
        return service.create(installation);
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }


    @PostMapping("/by-applications")
    public ResponseEntity<List<Installation>> getInstallationsByApplicationIds(@RequestBody List<Long> applicationIds) {
        try {
            List<Installation> installations = installationRepository.findByApplicationIdIn(applicationIds);
            return ResponseEntity.ok(installations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }


    @PatchMapping("/{id}/schedule")
    public ResponseEntity<Installation> scheduleInstallation(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Optional<Installation> optionalInstallation = installationRepository.findById(id);

        if (optionalInstallation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Installation installation = optionalInstallation.get();

        // Update date if provided
        if (updates.containsKey("date")) {
            String dateStr = (String) updates.get("date");
            installation.setDate(LocalDate.parse(dateStr));
        }

        // Update status to SCHEDULED
        installation.setStatus("SCHEDULED");

        Installation savedInstallation = installationRepository.save(installation);
        return ResponseEntity.ok(savedInstallation);
    }
}