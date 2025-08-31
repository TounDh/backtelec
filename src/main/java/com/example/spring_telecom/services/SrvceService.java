package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Srvce;
import com.example.spring_telecom.repositories.SrvceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvceService {
    @Autowired
    private SrvceRepository repository;
    @Autowired
    private SrvceRepository srvceRepository;

    public List<Srvce> getAllServicesWithOffers() {
        return srvceRepository.findAllWithOffers();
    }

    public Srvce getServiceByIdWithOffers(Long id) {
        return srvceRepository.findByIdWithOffers(id).orElseThrow();
    }

    public List<Srvce> getAll() {
        return repository.findAll();
    }

    public Srvce getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Srvce create(Srvce srvce) {
        if (srvce.getId() != null && srvce.getId() == 0) {
            srvce.setId(null);
        }
        return repository.save(srvce);
    }

    public Srvce update(Long id, Srvce srvce) {
        Srvce existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setName(srvce.getName());
            existing.setDescription(srvce.getDescription());
            existing.setInstallationFees(srvce.getInstallationFees());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}