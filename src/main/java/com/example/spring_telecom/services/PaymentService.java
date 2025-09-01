package com.example.spring_telecom.services;

import com.example.spring_telecom.entities.Payment;
import com.example.spring_telecom.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    @Autowired
    private PaymentRepository paymentRepository;


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



    public List<Payment> getAllPaymentsWithDetails() {
        return repository.findAllWithApplicationAndUserAndService();
    }

    public List<Payment> searchPayments(String searchTerm, String status, LocalDate date) {
        return repository.searchPayments(searchTerm, status, date);
    }

    public Payment updatePaymentStatus(Long id, String status) {
        Optional<Payment> optionalPayment = repository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setStatus(status);
            return repository.save(payment);
        }
        return null;
    }




    public List<Payment> getPaymentsByStatus(String status) {
        return paymentRepository.findByStatus(status);
    }

    public List<Payment> getOverduePayments() {
        return paymentRepository.findOverduePayments();
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }
}