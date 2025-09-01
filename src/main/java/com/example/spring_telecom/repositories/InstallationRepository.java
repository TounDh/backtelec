package com.example.spring_telecom.repositories;

import com.example.spring_telecom.entities.Installation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstallationRepository extends JpaRepository<Installation, Long> {
    List<Installation> findByApplicationIdIn(List<Long> applicationIds);
    // In InstallationRepository.java
    @Query("SELECT i FROM Installation i " +
            "JOIN FETCH i.application a " +
            "JOIN FETCH a.user u " +
            "JOIN FETCH a.srvce s")
    List<Installation> findAllWithApplicationAndUserAndService();

}