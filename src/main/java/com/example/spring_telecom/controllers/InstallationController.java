package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Installation;
import com.example.spring_telecom.services.InstallationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/installations")
public class InstallationController {
    @Autowired
    private InstallationService service;

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
}