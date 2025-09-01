package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Installation;
import com.example.spring_telecom.repositories.InstallationRepository;
import com.example.spring_telecom.services.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public Installation update(@PathVariable Long id, @RequestBody Installation installation) {
        return service.update(id, installation);
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
    public ResponseEntity<Installation> scheduleInstallation(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String date = body.get("date");
        if (date == null) {
            return ResponseEntity.badRequest().build();
        }
        Installation installation = service.getById(id);
        if (installation == null) {
            return ResponseEntity.notFound().build();
        }
        installation.setDate(date);
        installation.setStatus("SCHEDULED");
        return ResponseEntity.ok(service.update(id, installation));
    }
}