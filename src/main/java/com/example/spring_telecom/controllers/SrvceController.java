package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Srvce;
import com.example.spring_telecom.services.SrvceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/srvces")
public class SrvceController {
    @Autowired
    private SrvceService service;

    @GetMapping
    public List<Srvce> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Srvce getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Srvce create(@RequestBody Srvce srvce) {
        return service.create(srvce);
    }

    @PutMapping("/{id}")
    public Srvce update(@PathVariable Long id, @RequestBody Srvce srvce) {
        return service.update(id, srvce);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}