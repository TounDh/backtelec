package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Offer;
import com.example.spring_telecom.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    @Autowired
    private OfferService service;

    @GetMapping
    public List<Offer> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Offer getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Offer create(@RequestBody Offer offer) {
        return service.create(offer);
    }

    @PutMapping("/{id}")
    public Offer update(@PathVariable Long id, @RequestBody Offer offer) {
        return service.update(id, offer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}