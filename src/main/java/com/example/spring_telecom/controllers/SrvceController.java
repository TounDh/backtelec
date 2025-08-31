package com.example.spring_telecom.controllers;

import com.example.spring_telecom.entities.Offer;
import com.example.spring_telecom.entities.Srvce;
import com.example.spring_telecom.repositories.OfferRepository;
import com.example.spring_telecom.repositories.SrvceRepository;
import com.example.spring_telecom.services.SrvceService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return service.getAllServicesWithOffers(); // Use the new method
    }

    @GetMapping("/{id}")
    public Srvce getById(@PathVariable Long id) {
        return service.getServiceByIdWithOffers(id); // Use the new method
    }



    @PutMapping("/{id}")
    public Srvce update(@PathVariable Long id, @RequestBody Srvce updatedService) {
        Srvce existingService = srvceRepository.findById(id).orElseThrow();

        // Update basic service fields
        existingService.setName(updatedService.getName());
        existingService.setDescription(updatedService.getDescription());
        existingService.setInstallationFees(updatedService.getInstallationFees());

        // Clear existing offers and add new ones
        existingService.getOffers().clear();
        if (updatedService.getOffers() != null) {
            for (Offer offer : updatedService.getOffers()) {
                Offer newOffer = new Offer();
                newOffer.setPrice(offer.getPrice());
                newOffer.setSpeed(offer.getSpeed());
                newOffer.setCommitment(offer.getCommitment());
                existingService.addOffer(newOffer);
            }
        }

        return srvceRepository.save(existingService);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            // First, delete all offers associated with this service
            List<Offer> existingOffers = offerRepository.findBySrvceId(id);
            if (!existingOffers.isEmpty()) {
                offerRepository.deleteAll(existingOffers);
            }

            // Then delete the service
            srvceRepository.deleteById(id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting service: " + e.getMessage());
        }
    }

    @PostMapping
    @Transactional
    public Srvce createService(@RequestBody Srvce serviceRequest) {
        // First save the service to generate ID
        Srvce savedService = srvceRepository.save(serviceRequest);

        // Ensure offers are properly linked to the service
        if (serviceRequest.getOffers() != null) {
            for (Offer offer : serviceRequest.getOffers()) {
                offer.setSrvce(savedService); // This is crucial!
                offerRepository.save(offer);
            }
        }

        // Return the complete service with offers
        return srvceRepository.findById(savedService.getId()).orElseThrow();
    }
}