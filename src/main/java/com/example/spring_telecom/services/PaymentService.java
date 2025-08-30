package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Payment;
import com.example.spring_telecom.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Payment getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Payment create(Payment payment) {
        if (payment.getId() != null && payment.getId() == 0) {
            payment.setId(null);
        }
        return repository.save(payment);
    }

    public Payment update(Long id, Payment payment) {
        Payment existing = repository.findById(id).orElse(null);
        if (existing != null) {
            existing.setDeadline(payment.getDeadline());
            existing.setTotal(payment.getTotal());
            existing.setStatus(payment.getStatus());
            existing.setApplication(payment.getApplication());
            return repository.save(existing);
        }
        return null;
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}