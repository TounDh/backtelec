package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Role;
import com.example.spring_telecom.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private RoleService service;

    @GetMapping
    public List<Role> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Role getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Role create(@RequestBody Role role) {
        return service.create(role);
    }

    @PutMapping("/{id}")
    public Role update(@PathVariable Long id, @RequestBody Role role) {
        return service.update(id, role);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}