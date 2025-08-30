package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}