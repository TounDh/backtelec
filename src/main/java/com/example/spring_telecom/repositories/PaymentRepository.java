package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByApplicationId(Long applicationId);
    List<Payment> findByApplicationIdIn(List<Long> applicationIds);
}