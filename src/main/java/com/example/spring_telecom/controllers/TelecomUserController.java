package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.TelecomUser; // Add this import
import com.example.spring_telecom.services.TelecomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class TelecomUserController {
    @Autowired
    private TelecomUserService service;

    @GetMapping
    public List<TelecomUser> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public TelecomUser getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public TelecomUser create(@RequestBody TelecomUser user) {
        return service.create(user);
    }

    @PutMapping("/{id}")
    public TelecomUser update(@PathVariable Long id, @RequestBody TelecomUser user) {
        return service.update(id, user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}