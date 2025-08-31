package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find applications by user ID
    List<Application> findByUserId(Long userId);

    // Find applications by service ID
    List<Application> findBySrvceId(Long serviceId);

    // Find applications by status
    List<Application> findByStatus(String status);

    // Find applications by user ID and status
    List<Application> findByUserIdAndStatus(Long userId, String status);

    // Find applications by date range
    List<Application> findByApplicationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find applications by user ID and date range
    @Query("SELECT a FROM Application a WHERE a.user.id = :userId AND a.applicationDate BETWEEN :startDate AND :endDate")
    List<Application> findByUserIdAndDateRange(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Count applications by status
    long countByStatus(String status);

    // Count applications by user ID
    long countByUserId(Long userId);
}