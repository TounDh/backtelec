package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Offer;
import com.example.spring_telecom.repositories.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    @Autowired
    private OfferRepository repository;

    public List<Offer> getAll() {
        return repository.findAll();
    }

    public Offer getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Offer create(Offer offer) {
        if (offer.getId() != null && offer.getId() == 0) {
            offer.setId(null);
        }
        return repository.save(offer);
    }

    public Offer update(Long id, Offer offer) {
        Offer existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setCommitment(offer.getCommitment());
            existing.setSpeed(offer.getSpeed());
            existing.setPrice(offer.getPrice());
            existing.setSrvce(offer.getSrvce());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}