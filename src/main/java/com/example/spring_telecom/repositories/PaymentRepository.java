// PaymentRepository.java
package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find all payments with application, user, and service details
    @Query("SELECT p FROM Payment p " +
            "JOIN FETCH p.application a " +
            "JOIN FETCH a.user u " +
            "JOIN FETCH a.srvce s")
    List<Payment> findAllWithApplicationAndUserAndService();

    // Search payments with filters
    @Query("SELECT p FROM Payment p " +
            "JOIN FETCH p.application a " +
            "JOIN FETCH a.user u " +
            "JOIN FETCH a.srvce s " +
            "WHERE (:searchTerm IS NULL OR " +
            "       LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "       LOWER(u.surname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "       LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) AND " +
            "      (:status IS NULL OR p.status = :status) AND " +
            "      (:date IS NULL OR p.deadline = :date)")
    List<Payment> searchPayments(@Param("searchTerm") String searchTerm,
                                 @Param("status") String status,
                                 @Param("date") LocalDate date);

    // Find payments by application IDs
    List<Payment> findByApplicationIdIn(List<Long> applicationIds);

    // Find payments by status
    List<Payment> findByStatus(String status);

    // Find overdue payments (deadline before today and status not PAID)
    @Query("SELECT p FROM Payment p WHERE p.deadline < CURRENT_DATE AND p.status != 'PAID'")
    List<Payment> findOverduePayments();

    // Find payments by user ID
    @Query("SELECT p FROM Payment p JOIN p.application a WHERE a.user.id = :userId")
    List<Payment> findByUserId(@Param("userId") Long userId);
}