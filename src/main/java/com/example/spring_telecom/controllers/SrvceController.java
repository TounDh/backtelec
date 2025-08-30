package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Offer;
import com.example.spring_telecom.entities.Srvce;
import com.example.spring_telecom.repositories.OfferRepository;
import com.example.spring_telecom.repositories.SrvceRepository;
import com.example.spring_telecom.services.SrvceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/srvces")
public class SrvceController {
    @Autowired
    private SrvceService service;
    @Autowired
    private SrvceService srvceService;
    @Autowired
    private SrvceRepository srvceRepository;
    @Autowired
    private OfferRepository offerRepository;

    @GetMapping
    public List<Srvce> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Srvce getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PutMapping("/{id}")
    public Srvce update(@PathVariable Long id, @RequestBody Srvce srvce) {
        return service.update(id, srvce);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping
    public Srvce createService(@RequestBody Srvce service) {
        Srvce savedService = srvceRepository.save(service);

        // Link offers to the service and save them
        if (service.getOffers() != null) {
            for (Offer offer : service.getOffers()) {
                offer.setSrvce(savedService);
                offerRepository.save(offer);
            }
        }

        return srvceRepository.findById(savedService.getId()).orElseThrow();
    }
}